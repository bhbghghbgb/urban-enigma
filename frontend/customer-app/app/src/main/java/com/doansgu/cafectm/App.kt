package com.doansgu.cafectm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources


private const val PREFS_NAME = "MyPrefsAppCoffee"
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        val resources: Resources
            get() = mContext!!.resources
        val sharedPreferences: SharedPreferences
            get() = mContext!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}