package com.example.learncode.repository

import com.example.learncode.model.AddToCartRequest
import com.example.learncode.model.Cart
import com.example.learncode.model.ResponseFromServer
import com.example.learncode.model.RetrofitInstance
import retrofit2.Response

class CartRepository {
    private val cartService = RetrofitInstance.apiService

    suspend fun getCartOfUser(): Response<Cart> {
        return cartService.getCartOfUser()
    }

    suspend fun addToCart(
        addToCartRequest: AddToCartRequest
    ): Response<ResponseFromServer> {
        return cartService.addToCart(addToCartRequest)
    }

    suspend fun deleteProductOfCart(
        addToCartRequest: AddToCartRequest
    ): Response<ResponseFromServer> {
        return cartService.deleteProductOfCart(addToCartRequest)
    }
}