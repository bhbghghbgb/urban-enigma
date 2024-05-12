package com.example.delivery_app.data.repository

import com.example.delivery_app.data.model.Order
import com.example.delivery_app.data.model.RetrofitInstance
import retrofit2.Response

class DeliveryRepository {
    private val deliveryService = RetrofitInstance.apiService
    suspend fun getOrderByStaff(): Response<Order> {
        return deliveryService.getOrderByStaff()
    }

    suspend fun getOrdersByStaff(): Response<List<Order>> {
        return deliveryService.getOrdersByStaff()
    }
}