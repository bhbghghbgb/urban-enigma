package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.HomeScreen2Products
import com.doansgu.cafectm.model.RetrofitInstance

object ProductRepository {
    private val productService = RetrofitInstance.apiService
    suspend fun getHomeScreen2Products(): HomeScreen2Products? {
        return productService.getHomeScreen2Products().body()
    }
}