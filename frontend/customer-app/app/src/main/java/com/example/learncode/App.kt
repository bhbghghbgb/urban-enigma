package com.example.learncode

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources


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
    }
}