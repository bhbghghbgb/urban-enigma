package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.RetrofitInstance
import com.doansgu.cafectm.model.UserClass
import retrofit2.Response

class ProfileRepository {
    private val profileService = RetrofitInstance.apiService

    suspend fun getInfoOfCustomer(): Response<UserClass.Customer> {
        return profileService.getInfoOfCustomer()
    }
}