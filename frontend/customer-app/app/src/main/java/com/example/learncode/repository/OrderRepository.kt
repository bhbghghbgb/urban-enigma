package com.example.learncode.repository

import com.example.learncode.model.Order
import com.example.learncode.model.RetrofitInstance
import retrofit2.Response

class OrderRepository {
    private val orderService = RetrofitInstance.apiService
    suspend fun getOrdersNotYetDelivered(): Response<List<Order>> {
        return orderService.getOrdersNotYetDelivered()
    }

    suspend fun getOrdersDelivered(): Response<List<Order>> {
        return orderService.getOrdersDelivered()
    }

    suspend fun getOrderById(id: String): Response<Order> {
        return orderService.getOrderById(id)
    }
}