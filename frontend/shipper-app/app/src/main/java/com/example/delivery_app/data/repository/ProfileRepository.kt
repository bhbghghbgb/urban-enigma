package com.example.delivery_app.data.repository

import com.example.delivery_app.data.model.Account
import com.example.delivery_app.data.model.AuthResult
import com.example.delivery_app.data.model.RetrofitInstance
import com.example.delivery_app.data.model.Staff
import com.example.delivery_app.data.model.Token
import okhttp3.ResponseBody
import retrofit2.Response

class ProfileRepository {
    private val profileService = RetrofitInstance.apiService

    suspend fun getInfoOfUser(token: String): Response<Staff> {
        return profileService.getInfoOfUser(token)
    }
}