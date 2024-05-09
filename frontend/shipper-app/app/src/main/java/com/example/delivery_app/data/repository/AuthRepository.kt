package com.example.delivery_app.data.repository

import com.example.delivery_app.data.model.Account
import com.example.delivery_app.data.model.AuthResult
import com.example.delivery_app.data.model.ResponseFromServer
import com.example.delivery_app.data.model.RetrofitInstance
import com.example.delivery_app.data.model.Token
import okhttp3.ResponseBody
import retrofit2.Response

class AuthRepository {
    private val authService = RetrofitInstance.apiService

    suspend fun login(username: String, password: String): Response<Token> {
        return authService.login(Account(username, password))
    }

    suspend fun logout(token: String): Response<ResponseFromServer> {
        return authService.logout(token)
    }

    suspend fun authenticate(token: String): Response<AuthResult> {
        return authService.authenticate(token)
    }
}