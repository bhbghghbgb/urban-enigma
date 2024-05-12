package com.example.delivery_app.data.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/helloworld")
    suspend fun helloBackend(): Response<ResponseBody>

    @GET("/auth/staff")
    suspend fun testAuthorization(): Response<ResponseBody>

    @GET("/user")
    suspend fun getInfoOfUser(): Response<Staff>

    @GET("/delivery/order")
    suspend fun getOrderByStaff(): Response<Order>

    @GET("/delivery/order/delivered")
    suspend fun getOrdersByStaff(): Response<List<Order>>

    @GET("/orders/admin/{id}")
    suspend fun getOrderById(
        @Path("id") id: String
    ): Response<Order>
}