package com.example.learncode.repository

import com.example.learncode.model.Order
import com.example.learncode.model.RetrofitInstance
import retrofit2.Response

class OrderRepository {
    private val orderService = RetrofitInstance.apiService
    suspend fun getOrdersNotYetDelivered(token: String): Response<List<Order>> {
        return orderService.getOrdersNotYetDelivered(token)
    }
}