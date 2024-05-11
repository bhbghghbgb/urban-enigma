package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.Order
import com.doansgu.cafectm.model.RetrofitInstance
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