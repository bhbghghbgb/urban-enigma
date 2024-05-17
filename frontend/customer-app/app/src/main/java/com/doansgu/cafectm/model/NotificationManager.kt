package com.doansgu.cafectm.model

import android.util.Log
import com.doansgu.cafectm.App
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object NotificationManager {
    fun bindDeviceToCurrentUser() {
        CoroutineScope(Dispatchers.IO).launch {
            App.firebaseAuth.currentUser?.let {
                val deviceToken = FirebaseMessaging.getInstance().token.await()
                Log.d("NotificationManager", "bindDeviceToCurrentUser: ${it.uid}, $deviceToken")
                RetrofitInstance.apiService.bindDeviceNotification(deviceToken)
            }
        }

    }

    fun unbindDeviceFromCurrentUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val deviceToken = FirebaseMessaging.getInstance().token.await()
            Log.d("NotificationManager", "unbindDeviceFromCurrentUser: $deviceToken")
            FirebaseMessaging.getInstance().deleteToken().await()
            RetrofitInstance.apiService.unbindDeviceNotification()
        }
    }
}