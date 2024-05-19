package com.example.delivery_app.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET

import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @PUT("/notification/device-link")
    suspend fun bindDeviceNotification(
        @Query("device-token") deviceToken: String,
    ): Response<Unit>

    @DELETE("/notification/device-link")
    suspend fun unbindDeviceNotification(): Response<Unit>

    @POST("/user/increase-membership-point")
    suspend fun increaseMembershipPoint(@Body increasePoint: IncreasePoint) : Response<IncreasePointResponse>

    @PUT("/delivery/update/{id}")
    suspend fun updateStatusDelivery(@Path("id") id: String): Response<ResponseFromServer>

    @PATCH("/orders/update/{id}")
    suspend fun updateStatusOrder(@Path("id") id: String, @Body status: Status): Response<ResponseFromServer>

}