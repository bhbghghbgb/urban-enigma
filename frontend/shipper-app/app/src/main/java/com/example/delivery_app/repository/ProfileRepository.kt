package com.example.delivery_app.repository

import com.example.delivery_app.model.RetrofitInstance
import com.example.delivery_app.model.Staff
import retrofit2.Response

class ProfileRepository {
    private val profileService = RetrofitInstance.apiService

    suspend fun getInfoOfUser(): Response<Staff> {
        return profileService.getInfoOfUser()
    }
}