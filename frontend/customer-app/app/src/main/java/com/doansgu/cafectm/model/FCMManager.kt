package com.doansgu.cafectm.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.doansgu.cafectm.App
import com.doansgu.cafectm.NOTIFICATION_CHANNEL_ID
import com.doansgu.cafectm.NOTIFICATION_CHANNEL_NAME
import com.doansgu.cafectm.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

object FCMManager {
    private lateinit var notificationManager: NotificationManager

    fun bindDeviceToCurrentUser() {
        CoroutineScope(Dispatchers.IO).launch {
            App.firebaseAuth.currentUser?.let {
                val deviceToken = FirebaseMessaging.getInstance().token.await()
                Log.d("FCMManager", "bindDeviceToCurrentUser: ${it.uid}, $deviceToken")
                RetrofitInstance.apiService.bindDeviceNotification(deviceToken)
            }
        }

    }

    fun unbindDeviceFromCurrentUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val deviceToken = FirebaseMessaging.getInstance().token.await()
            Log.d("FCMManager", "unbindDeviceFromCurrentUser: $deviceToken")
            FirebaseMessaging.getInstance().deleteToken().await()
            RetrofitInstance.apiService.unbindDeviceNotification()
        }
    }

    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = NOTIFICATION_CHANNEL_NAME
            val descriptionText = NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            notificationManager =
                App.mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun postForegroundNotification(title: String?, body: String?) {
        val notification = NotificationCompat.Builder(App.mContext!!, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title).setContentText(body).setSmallIcon(R.drawable.ic_launcher_foreground).build()
        notificationManager.notify(Random.nextInt(), notification)
    }
}