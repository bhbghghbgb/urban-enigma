package com.example.delivery_app.data.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: Account): Response<Token>

    @POST("/logout")
    suspend fun logout(): Response<ResponseBody>

    @GET("/authenticate")
    suspend fun authenticate(@Header("Authorization") token: String): Response<AuthResult>

    @GET("/user")
    suspend fun getInfoOfUser(@Header("Authorization") token: String): Response<Staff>
}