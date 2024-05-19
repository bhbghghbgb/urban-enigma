package com.example.delivery_app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.example.delivery_app.model.FCMManager.createNotificationChannel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private const val PREFS_NAME = "MyPrefsAppCoffee"

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        createNotificationChannel()
        firebaseAuth.setLanguageCode("vi")
        firebaseAuth.firebaseAuthSettings.forceRecaptchaFlowForTesting(true)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null
        val resources: Resources
            get() = mContext!!.resources
        val sharedPreferences: SharedPreferences
            get() = mContext!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val firebaseAuth: FirebaseAuth
            get() = Firebase.auth
    }
}