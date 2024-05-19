package com.example.delivery_app.repository

import com.example.delivery_app.model.Order
import com.example.delivery_app.model.ResponseFromServer
import com.example.delivery_app.model.RetrofitInstance
import com.example.delivery_app.model.Status
import retrofit2.Response

class OrderRepository {
    private val orderService = RetrofitInstance.apiService
    suspend fun getOrderById(id: String): Response<Order> {
        return orderService.getOrderById(id)
    }
    suspend fun updateStatusDelivery(id: String): Response<ResponseFromServer> {
        return orderService.updateStatusDelivery(id)
    }
    suspend fun updateStatusOrder(id: String, status: Status): Response<ResponseFromServer> {
        return orderService.updateStatusOrder(id, status)
    }
}