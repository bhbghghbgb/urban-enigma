package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.RetrofitInstance

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