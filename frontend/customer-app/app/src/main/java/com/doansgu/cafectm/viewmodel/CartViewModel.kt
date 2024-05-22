package com.doansgu.cafectm.viewmodel

import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.CART_UPDATE_DEBOUNCE_DURATION
import com.doansgu.cafectm.model.AddToCartRequest
import com.doansgu.cafectm.model.Cart
import com.doansgu.cafectm.model.OrderRequest
import com.doansgu.cafectm.model.PaymentState
import com.doansgu.cafectm.model.ProductOfCart
import com.doansgu.cafectm.model.UserClass
import com.doansgu.cafectm.repository.CartRepository
import com.doansgu.cafectm.repository.OrderRepository
import com.doansgu.cafectm.repository.ProfileRepository
import com.doansgu.cafectm.util.Zalopay.Api.CreateOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.util.Locale

class CartViewModel : ViewModel() {
    private val repository = CartRepository()
    private val orderRepository = OrderRepository()
    private val customerRepository = ProfileRepository()

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    //    Dùng debounce để theo dõi sự thay đổi của giỏ hàng
//    Nếu giỏ hàng thay đổi, sẽ gửi request lên server
    private val cartUpdateFlow = cart.asFlow().debounce(CART_UPDATE_DEBOUNCE_DURATION)

    private val _isValidAddToCart = MutableLiveData<Boolean>()
    val isValidAddToCart: LiveData<Boolean> = _isValidAddToCart

    private val _isValidDeleteProduct = MutableLiveData<Boolean>()
    val isValidDeleteProduct: LiveData<Boolean> = _isValidDeleteProduct

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Loading)
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    private val _total = MutableStateFlow<Double>(0.0)
    val total: StateFlow<Double> = _total

    private val _requestPay = Channel<String>()
    val requestPay = _requestPay.receiveAsFlow()

    private val _userData = MutableLiveData<UserClass.Customer>()
    val userData: LiveData<UserClass.Customer> = _userData

    init {
//    Sử dụng flow để cập nhật giỏ hàng, giảm thiểu việc gửi request lên server bằng Debounce
        viewModelScope.launch {
            fetchData()
            cartUpdateFlow.collect {
                if (it !== null) {
                    updateCart()
                }
            }
        }
    }

    fun fetchData() {
        getCardOfUser()
        caculateTotal()
        getInfoUser()
    }

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
            val productToUpdate = mutableProducts.find { it.product.id == productId }

            productToUpdate?.let { product ->
                val updatedQuantity = product.amount + 1
                val updatedPrice = product.product.price?.times(updatedQuantity)
                val updatedTotal = cart.total + product.product.price!!
                val updatedProductIndex = mutableProducts.indexOf(product)
                if (updatedProductIndex != -1) {
                    mutableProducts[updatedProductIndex] = updatedPrice?.let {
                        product.copy(
                            amount = updatedQuantity, price = it
                        )
                    }!!
                }
                _cart.value = cart.copy(
                    total = updatedTotal, products = mutableProducts
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
        productId: String, addToCartRequest: AddToCartRequest
    ) {
        _cart.value?.let { cart ->
            val mutableProducts = cart.products.toMutableList()
            val productToUpdate = mutableProducts.find { it.product.id == productId }
            productToUpdate?.let { product ->
                val updatedQuantity = product.amount - 1
                val updatedPrice = product.product.price?.times(updatedQuantity)
                val updatedTotal = cart.total - product.product.price!!
                if (updatedQuantity == 0) {
                    deleteProduct(addToCartRequest);
                    if (isValidDeleteProduct.value == true) {
                        mutableProducts.remove(product)
                    }
                } else {
                    val updatedProductIndex = mutableProducts.indexOf(product)
                    if (updatedProductIndex != -1) {
                        mutableProducts[updatedProductIndex] = updatedPrice?.let {
                            product.copy(
                                amount = updatedQuantity, price = it
                            )
                        }!!
                    }
                }
                _cart.value = cart.copy(
                    total = updatedTotal, products = mutableProducts
                )
            }
        }
    }

    fun caculateTotal() {
        val total = _cart.value?.products?.sumByDouble { it.price * it.amount } ?: 0.0
        _total.value = total
    }

    private fun updateTotalOfCart(priceChange: Double, detailOfCart: ProductOfCart, amount: Int) {
        _total.value += priceChange
        val newProducts = cart.value!!.products.map {
            if (it.product.id == detailOfCart.product.id) {
                return@map it.copy(amount = amount)
            }
            it
        }
        _cart.value = cart.value?.copy(total = total.value, products = newProducts)
    }

    fun increase(price: Double, detailOfCart: ProductOfCart, amount: Int) {
        updateTotalOfCart(price, detailOfCart, amount)
    }

    fun decrease(price: Double, detailOfCart: ProductOfCart, amount: Int) {
        updateTotalOfCart(-price, detailOfCart, amount)
    }

    fun updateCart() {
        viewModelScope.launch {
            try {
                val response = repository.updateCart(
                    _cart.value!!
                )
                if (!response.isSuccessful) {
                    Log.e("Update cart", "Error updating cart")
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ
                e.printStackTrace()
            }
        }
    }


    fun getInfoUser() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = customerRepository.getInfoOfCustomer()
                if (response.isSuccessful) {
                    Log.d("Get User Info Success", response.toString())
                    _userData.postValue(response.body())
                } else {
                    Log.d("abc", response.toString())
                }
            } catch (ex: Exception) {
                Log.d("Get User Info Error", ex.toString())
            }
        }
    }

    fun createOders(
        discount: Int,
        address: String,
        note: String,
        paymentMethod: String,
    ) {
        var result = false
        viewModelScope.launch {
            try {
                val orderRequest = _cart.value?.let {
                    OrderRequest(
                        it.products, discount, address, note, paymentMethod
                    )
                }
                val response = orderRequest?.let { orderRepository.createOrder(orderRequest) }
                if (!response!!.isSuccessful) {
                    Log.e("Create Order Error", "Error creating order")
                }
            } catch (e: Exception) {
                // Handle the exception
                Log.e("Network Error", "Error: ${e.message}")
            }
        }
    }

    fun pay() {
        viewModelScope.launch {
            val token = createOrderZalo()
            if (token.isNotEmpty()) {
                _requestPay.send(token)
            }
        }
    }

    fun pay(activity: Activity, token: String) {
        viewModelScope.launch {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            if (token.isNotEmpty()) {
                ZaloPaySDK.getInstance()
                    .payOrder(activity, token, "demozpdk://app", object : PayOrderListener {
                        override fun onPaymentSucceeded(
                            transactionId: String?, transToken: String?, appId: String?
                        ) {
//                        TODO("Cập nhật giá trị của susses")
                            _paymentState.value = PaymentState.Success
                        }

                        override fun onPaymentCanceled(p0: String?, p1: String?) {
//                        TODO("Cập nhật giá trị on canceled")
                            _paymentState.value = PaymentState.Cancel

                        }

                        override fun onPaymentError(p0: ZaloPayError?, p1: String?, p2: String?) {
                            _paymentState.value = PaymentState.Error
                            Log.d("Zalo Pau Error ", p0.toString())
                        }
                    })
            }
        }
    }

    fun closeDialog() {
        _paymentState.value = PaymentState.Loading
    }

    private fun convertToVND(total: Double): Double {
        val exchangeRate = 23000.0
        return total * exchangeRate
    }

    fun createOrderZalo(): String {
        val orderAPI: CreateOrder = CreateOrder()
        try {
            val totalString = String.format("%.0f", convertToVND(_total.value))
            val data: JSONObject = orderAPI.createOrder(totalString)
            Log.d("Test Total", totalString)
            val code = data.getString("return_code")
            if (code == "1") {
                return data.getString("zp_trans_token")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }


}