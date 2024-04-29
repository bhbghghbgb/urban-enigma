package com.example.learncode.repository

import com.example.learncode.model.AddToCartRequest
import com.example.learncode.model.Cart
import com.example.learncode.model.ResponseFromServer
import com.example.learncode.model.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Response

class CartRepository {
    private val cartService = RetrofitInstance.apiService

    suspend fun getCartOfUser(token: String): Response<Cart> {
        return cartService.getCartOfUser(token)
    }

    suspend fun addToCart(
        token: String,
        addToCartRequest: AddToCartRequest
    ): Response<ResponseFromServer> {
        return cartService.addToCart(token, addToCartRequest)
    }

    suspend fun deleteProductOfCart(
        token: String,
        addToCartRequest: AddToCartRequest
    ): Response<ResponseFromServer> {
        return cartService.deleteProductOfCart(token, addToCartRequest)
    }
}