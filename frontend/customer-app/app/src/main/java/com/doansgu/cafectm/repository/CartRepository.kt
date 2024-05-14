package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.AddToCartRequest
import com.doansgu.cafectm.model.Cart
import com.doansgu.cafectm.model.ResponseFromServer
import com.doansgu.cafectm.model.RetrofitInstance
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

    suspend fun updateCart(
        updateCartRequest: Cart
    ): Response<ResponseFromServer> {
        return cartService.updateCart(updateCartRequest)
    }
}