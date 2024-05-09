package com.example.cart.Utilities.API

import com.example.cart.Models.Cart
import com.example.cart.Models.DetailOfCart
import com.example.cart.Models.ResponseFromServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {
    @GET("/v1/api/cart/{customerId}")
    suspend fun getCartForCustomer(@Path("customerId") customerId: String): Response<Cart>
    @PATCH("v1/api/cart/{customer}/increase/{product}")
    suspend fun increaseAmountOfProduct(
        @Path("customer") customerId: String,
        @Path("product") productId: String,
        @Body amount: Int
    ): Response<Cart>

    @PUT("/v1/api/cart/{customer}/update")
    suspend fun updateCart(
        @Path("customer") customerId: String,
        @Body cart: Cart
    ): Response<ResponseFromServer>
}
