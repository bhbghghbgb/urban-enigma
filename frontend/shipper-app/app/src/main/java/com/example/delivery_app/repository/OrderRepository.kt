package com.example.delivery_app.repository

import com.example.delivery_app.model.Order
import com.example.delivery_app.model.RetrofitInstance
import retrofit2.Response

class OrderRepository {
    private val orderService = RetrofitInstance.apiService
    suspend fun getOrderById(id: String): Response<Order> {
        return orderService.getOrderById(id)
    }
}