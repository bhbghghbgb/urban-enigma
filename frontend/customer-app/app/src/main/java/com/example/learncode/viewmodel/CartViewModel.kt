package com.example.learncode.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.AddToCartRequest
import com.example.learncode.model.Cart
import com.example.learncode.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getCardOfUser(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCartOfUser(token)
                if (response.isSuccessful) {
                    _cart.postValue(response.body())
                } else {
                }
            } catch (e: Exception) {
                Log.d("Data", e.message.toString())
            }
        }
    }

    fun addToCart(token: String, addToCartRequest: AddToCartRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.addToCart(token, addToCartRequest)
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

    fun deleteProduct(token: String, addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {
            try {
                val response = repository.deleteProductOfCart(token, addToCartRequest)
                if (response.isSuccessful) {
                    if (response.body()?.message != null) {
                        val message = response.body()?.message
                        if (message == "Product has been removed from the cart") {
                            _isValidDeleteProduct.postValue(true)
                            getCardOfUser(token)
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
        token: String,
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
                    deleteProduct(token, addToCartRequest);
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
}