package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.RetrofitInstance

object NotificationRepository {
    suspend fun bindDeviceToCurrentUser(deviceToken: String) {
        try {
            RetrofitInstance.apiService.bindDeviceNotification(deviceToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun unbindDeviceFromCurrentUser() {
        try {
            RetrofitInstance.apiService.unbindDeviceNotification()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}