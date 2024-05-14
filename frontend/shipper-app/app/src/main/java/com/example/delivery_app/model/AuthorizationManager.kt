package com.example.delivery_app.model

import com.example.delivery_app.AUTHORIZATION_PREFERENCE_KEY
import com.example.delivery_app.App

class AuthorizationManager {
    companion object {
        var authorization: String?
            get() = App.sharedPreferences.getString(AUTHORIZATION_PREFERENCE_KEY, null)
            set(value) = App.sharedPreferences.edit().putString(AUTHORIZATION_PREFERENCE_KEY, value)
                .apply()

        fun clearAuthorization() {
            App.sharedPreferences.edit().remove(AUTHORIZATION_PREFERENCE_KEY).apply()
        }
    }

}