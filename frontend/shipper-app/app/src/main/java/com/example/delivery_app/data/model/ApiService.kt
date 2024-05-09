package com.example.delivery_app.data.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: Account): Response<Token>

    @POST("/logout")
    suspend fun logout(): Response<ResponseBody>

    @GET("/authenticate")
    suspend fun authenticate(@Header("Authorization") token: String): Response<AuthResult>

    @GET("/user")
    suspend fun getInfoOfUser(@Header("Authorization") token: String): Response<Staff>

    @GET("/delivery/order")
    suspend fun getOrderByStaff(@Header("Authorization") token: String): Response<Order>

    @GET("/delivery/order/delivered")
    suspend fun getOrdersByStaff(@Header("Authorization") token: String): Response<List<Order>>

    @GET("/orders/admin/{id}")
    suspend fun getOrderById(
        @Path("id") id: String
    ): Response<Order>

    @POST("/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ResponseFromServer>
}