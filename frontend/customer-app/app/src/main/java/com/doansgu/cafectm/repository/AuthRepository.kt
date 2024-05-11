package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.Account
import com.doansgu.cafectm.model.AuthResult
import com.doansgu.cafectm.model.ResponseFromServer
import com.doansgu.cafectm.model.RetrofitInstance
import com.doansgu.cafectm.model.Token
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