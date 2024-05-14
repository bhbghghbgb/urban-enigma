package com.example.delivery_app.repository

import com.example.delivery_app.model.RetrofitInstance

object AuthRepository {
    suspend fun testAuthorization(): Boolean =
        RetrofitInstance.apiService.testAuthorization().isSuccessful
}