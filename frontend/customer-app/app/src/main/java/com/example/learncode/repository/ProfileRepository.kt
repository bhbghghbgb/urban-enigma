package com.example.learncode.repository

import com.example.learncode.model.RetrofitInstance
import com.example.learncode.model.UserClass
import retrofit2.Response

class ProfileRepository {
    private val profileService = RetrofitInstance.apiService

    suspend fun getInfoOfCustomer(token: String): Response<UserClass.Customer> {
        return profileService.getInfoOfCustomer(token)
    }
}