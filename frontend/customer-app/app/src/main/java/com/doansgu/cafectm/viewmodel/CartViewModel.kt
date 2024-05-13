package com.doansgu.cafectm.viewmodel

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.AddToCartRequest
import com.doansgu.cafectm.model.Cart
import com.doansgu.cafectm.model.PaymentState
import com.doansgu.cafectm.repository.CartRepository
import com.doansgu.cafectm.util.Zalopay.Api.CreateOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.util.Locale

class CartViewModel : ViewModel() {
    private val repository = CartRepository()

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _isValidAddToCart = MutableLiveData<Boolean>()
    val isValidAddToCart: LiveData<Boolean> = _isValidAddToCart

    private val _isValidDeleteProduct = MutableLiveData<Boolean>()
    val isValidDeleteProduct: LiveData<Boolean> = _isValidDeleteProduct

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _paymentState = MutableStateFlow<PaymentState?>(null)
    val paymentState: StateFlow<PaymentState?> = _paymentState

    fun reverseGeocode(context: Context, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)!!
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val addressStringBuilder = StringBuilder()
                    for (i in 0..address.maxAddressLineIndex) {
                        addressStringBuilder.append(address.getAddressLine(i)).append(", ")
                    }
                    _address.postValue(addressStringBuilder.toString().removeSuffix(", "))
                } else {
                    _address.postValue("")
                }
            } catch (e: java.io.IOException) {
                _address.postValue("")
                e.printStackTrace()
            }
        }
    }

    fun setAddress(address: String) {
        _address.postValue(address)
    }

    fun getCardOfUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCartOfUser()
                if (response.isSuccessful) {
                    _cart.postValue(response.body())
                }
            } catch (e: Exception) {
                Log.d("Data", e.message.toString())
            }
        }
    }

    fun addToCart(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.addToCart(addToCartRequest)
                if (response.isSuccessful) {
                    if (response.body()?.message != null) {
                        val message = response.body()?.message // Đây là biến không thay đổi
                        if (message == "Add to cart successfully!") {
                            _isValidAddToCart.postValue(true)
                        } else {
                            _isValidAddToCart.postValue(false)
                        }
                    }
                } else {
                    _isValidAddToCart.postValue(false)
                }
            } catch (e: Exception) {
                _isValidAddToCart.postValue(false)
            }
        }
    }

    fun increaseProductQuantity(productId: String) {
        _cart.value?.let { cart ->
            val mutableProducts = cart.products.toMutableList()
            val productToUpdate = mutableProducts.find { it.product._id == productId }

            productToUpdate?.let { product ->
                val updatedQuantity = product.amount + 1
                val updatedPrice = product.product.price * updatedQuantity
                val updatedTotal = cart.total + product.product.price
                val updatedProductIndex = mutableProducts.indexOf(product)
                if (updatedProductIndex != -1) {
                    mutableProducts[updatedProductIndex] = product.copy(
                        amount = updatedQuantity,
                        price = updatedPrice
                    )
                }
                _cart.value = cart.copy(
                    total = updatedTotal,
                    products = mutableProducts
                )
            }
        }
    }

    fun deleteProduct(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {
            try {
                val response = repository.deleteProductOfCart(addToCartRequest)
                if (response.isSuccessful) {
                    if (response.body()?.message != null) {
                        val message = response.body()?.message
                        if (message == "Product has been removed from the cart") {
                            _isValidDeleteProduct.postValue(true)
                            getCardOfUser()
                        } else {
                            _isValidDeleteProduct.postValue(false)
                        }
                    }
                } else {
                    _isValidDeleteProduct.postValue(false)
                }
            } catch (e: Exception) {
                _isValidDeleteProduct.postValue(false)
            }
        }
    }

    fun decreaseProductQuantity(
        productId: String,
        addToCartRequest: AddToCartRequest
    ) {
        _cart.value?.let { cart ->
            val mutableProducts = cart.products.toMutableList()
            val productToUpdate = mutableProducts.find { it.product._id == productId }
            productToUpdate?.let { product ->
                val updatedQuantity = product.amount - 1
                val updatedPrice = product.product.price * updatedQuantity
                val updatedTotal = cart.total - product.product.price
                if (updatedQuantity == 0) {
                    deleteProduct(addToCartRequest);
                    if (isValidDeleteProduct.value == true) {
                        mutableProducts.remove(product)
                    }
                } else {
                    val updatedProductIndex = mutableProducts.indexOf(product)
                    if (updatedProductIndex != -1) {
                        mutableProducts[updatedProductIndex] = product.copy(
                            amount = updatedQuantity,
                            price = updatedPrice
                        )
                    }
                }
                _cart.value = cart.copy(
                    total = updatedTotal,
                    products = mutableProducts
                )
            }
        }
    }

    fun updateCart() {
        viewModelScope.launch {
            try {
                val response = repository.updateCart(
                    _cart.value!!)
                if (!response.isSuccessful) {
                    Log.e("Update cart", "Error updating cart")
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ
                e.printStackTrace()
            }
        }
    }

    fun pay(activity: Activity) {
        viewModelScope.launch {
            val token = createOrder()
            if (token.isNotEmpty()) {
                ZaloPaySDK.getInstance().payOrder(activity, token, "demozpdk://app", object: PayOrderListener {
                    override fun onPaymentSucceeded(transactionId: String?, transToken: String?, appId: String?) {
//                        TODO("Cập nhật giá trị của susses")
                        _paymentState.value!!.onSusses = true;
                    }

                    override fun onPaymentCanceled(p0: String?, p1: String?) {
//                        TODO("Cập nhật giá trị on canceled")
                        _paymentState.value!!.onCancel = true;

                    }

                    override fun onPaymentError(p0: ZaloPayError?, p1: String?, p2: String?) {
//                        TODO("Cập nhật giá trị on failed")
                        _paymentState.value!!.onFailed = true;
                    }

                })
            }
        }
    }

    fun createOrder(): String {
        val orderAPI: CreateOrder = CreateOrder()
        try {
            val totalString = String.format("%.0f", _cart.value!!.total)
            val data: JSONObject = orderAPI.createOrder(totalString)
            Log.d("Test Total", totalString)
            val code = data.getString("return_code")
            if (code == "1") {
                return data.getString("zp_trans_token")
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
        return ""
    }


}