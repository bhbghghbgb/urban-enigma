package com.example.learncode.repository

import com.example.learncode.model.Account
import com.example.learncode.model.AuthResult
import com.example.learncode.model.RetrofitInstance
import com.example.learncode.model.Token
import okhttp3.ResponseBody
import retrofit2.Response

class AuthRepository {
    private val authService = RetrofitInstance.apiService

    suspend fun login(username: String, password: String): Response<Token> {
        return authService.login(Account(username, password))
    }

    suspend fun logout(): Response<ResponseBody> {
        return authService.logout()
    }

    suspend fun authenticate(token: String): Response<AuthResult> {
        return authService.authenticate(token)
    }
}