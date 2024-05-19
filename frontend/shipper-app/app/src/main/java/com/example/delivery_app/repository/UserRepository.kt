package com.example.delivery_app.repository

import com.example.delivery_app.model.IncreasePoint
import com.example.delivery_app.model.IncreasePointResponse
import com.example.delivery_app.model.RetrofitInstance
import retrofit2.Response

class UserRepository {
    private val userService = RetrofitInstance.apiService
    suspend fun increaseMembershipPoint(increasePoint: IncreasePoint): Response<IncreasePointResponse> {
        return userService.increaseMembershipPoint(increasePoint)
    }
}