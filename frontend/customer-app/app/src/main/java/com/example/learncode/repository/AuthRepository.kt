package com.example.learncode.repository

import com.example.learncode.model.Account
import com.example.learncode.model.AuthResult
import com.example.learncode.model.ResponseFromServer
import com.example.learncode.model.RetrofitInstance
import com.example.learncode.model.Token
import retrofit2.Response

class AuthRepository {
    private val authService = RetrofitInstance.apiService

    suspend fun login(username: String, password: String): Response<Token> {
        return authService.login(Account(username, password))
    }

    suspend fun logout(): Response<ResponseFromServer> {
        return authService.logout()
    }

    suspend fun authenticate(): Response<AuthResult> {
        return authService.authenticate()
    }
}