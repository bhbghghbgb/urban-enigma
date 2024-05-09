package com.example.cart.ViewModels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cart.Models.Cart
import com.example.cart.Models.DetailOfCart
import com.example.cart.Utilities.API.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel: ViewModel() {
    private val _cart = MutableStateFlow<Cart?>(null)
    val cart: StateFlow<Cart?> = _cart
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total
    // TODO: Implement the ViewModel

    init {
        fetchCart()
    }

    private fun fetchCart() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.retrofit.getCartForCustomer("661c85b6b64fb01f4066caeb")
                if (response.isSuccessful) {
                    _cart.value = response.body()
                    _total.value = _cart.value!!.total
                } else {
                    _cart.value = null
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ
            }
        }
    }


    fun increaseTotalOfCart(price: Double) {
        _total.value += price
        _cart.value!!.total = _total.value
        
    }
    fun decreaseTotalOfCart(price: Double) {
        _total.value -= price
        _cart.value!!.total = _total.value
    }

    fun updateAmountOfProduct(detailOfCart: DetailOfCart, amount: Int) {
        val index = _cart.value!!.products.indexOf(detailOfCart)
        _cart.value!!.products[index].amount = amount
    }

    fun updateCart() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.retrofit.updateCart("661c85b6b64fb01f4066caeb",
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
}