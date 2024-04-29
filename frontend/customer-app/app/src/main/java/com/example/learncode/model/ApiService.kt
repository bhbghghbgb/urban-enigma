package com.example.learncode.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/products/lists/popular")
    suspend fun getProductList(): Response<List<Products>>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<Products>

    @POST("/login")
    suspend fun login(@Body request: Account): Response<Token>

    @POST("/logout")
    suspend fun logout(): Response<ResponseBody>

    @GET("/authenticate")
    suspend fun authenticate(@Header("Authorization") token: String): Response<AuthResult>

    @GET("/cart")
    suspend fun getCartOfUser(@Header("Authorization") token: String): Response<Cart>

    @POST("/cart/add-to-cart")
    suspend fun addToCart(@Header("Authorization") token: String, @Body request: AddToCartRequest) :Response<ResponseFromServer>

    @PATCH("/cart/delete")
    suspend fun deleteProductOfCart(@Header("Authorization") token: String, @Body request: AddToCartRequest) :Response<ResponseFromServer>

    @GET("/orders/not-yet-delivered")
    suspend fun getOrdersNotYetDelivered(@Header("Authorization") token: String) : Response<List<Order>>
}