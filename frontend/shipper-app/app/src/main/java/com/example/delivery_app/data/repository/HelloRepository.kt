package com.example.delivery_app.data.repository

import com.example.delivery_app.data.model.RetrofitInstance


object HelloRepository {
    suspend fun helloBackend(): String? {
        try {
            return RetrofitInstance.apiService.helloBackend().body()?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            return e.message
        }
    }
}