package com.example.delivery_app.data.repository

import com.example.delivery_app.data.model.RetrofitInstance

object AuthRepository {
    suspend fun testAuthorization(): Boolean =
        RetrofitInstance.apiService.testAuthorization().isSuccessful
}