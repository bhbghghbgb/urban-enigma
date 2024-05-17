package com.doansgu.cafectm

import android.util.Log
import com.doansgu.cafectm.model.FCMManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // if user logged in, also update token to server
        FCMManager.bindDeviceToCurrentUser()
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "Message received")
        // show the notification anyway even if the app is in foreground
        FCMManager.postForegroundNotification(
            message.notification?.title, message.notification?.body
        )
    }
}