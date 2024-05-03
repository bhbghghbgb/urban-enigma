package com.example.learncode.model

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREFS_NAME = "MyPrefsAppCoffee"
    private const val TOKEN_KEY = "token"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String, context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }
}