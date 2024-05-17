package com.doansgu.cafectm

import android.util.Log
import com.doansgu.cafectm.model.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // if user logged in, also update token to server
        NotificationManager.bindDeviceToCurrentUser()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // TODO: handle notification
    }
}