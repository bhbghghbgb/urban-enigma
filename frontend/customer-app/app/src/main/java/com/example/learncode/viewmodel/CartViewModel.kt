package com.example.learncode.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.AddToCartRequest
import com.example.learncode.model.Cart
import com.example.learncode.model.Product
import com.example.learncode.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val repository = CartRepository()

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _isValidAddToCart = MutableLiveData<Boolean>()
    val isValidAddToCart: LiveData<Boolean> = _isValidAddToCart

    private val _isValidDeleteProduct = MutableLiveData<Boolean>()
    val isValidDeleteProduct: LiveData<Boolean> = _isValidDeleteProduct

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
                if(response.isSuccessful) {
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

    fun decreaseProductQuantity(productId: String) {
        _cart.value?.let { cart ->
            val mutableProducts = cart.products.toMutableList()
            val productToUpdate = mutableProducts.find { it.product._id == productId }

            productToUpdate?.let { product ->
                val updatedQuantity = product.amount - 1
                val updatedPrice = product.product.price * updatedQuantity
                val updatedTotal = cart.total - product.product.price
                if (updatedQuantity == 0) {
                    mutableProducts.remove(product)
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

    // Hàm để gửi các thay đổi đến máy chủ
//    fun submitChangesToServer(token: String) {
//        viewModelScope.launch(Dispatchers.Main) {
//            try {
//                // Gửi các thay đổi đến máy chủ
//                val response = repository.updateCartItems(token, _tempQuantityChanges)
//                if (response.isSuccessful) {
//                    // Nếu thành công, cập nhật LiveData cart với dữ liệu mới
//                    _cart.postValue(response.body())
//                    // Xóa các thay đổi tạm thời sau khi đã gửi thành công đến máy chủ
//                    _tempQuantityChanges.clear()
//                } else {
//                    // Xử lý lỗi nếu có
//                }
//            } catch(e: Exception) {
//                Log.d("Data", e.message.toString())
//            }
//        }
//    }

}