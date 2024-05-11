package com.example.learncode.model

import com.example.learncode.AUTHORIZATION_PREFERENCE_KEY
import com.example.learncode.App

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