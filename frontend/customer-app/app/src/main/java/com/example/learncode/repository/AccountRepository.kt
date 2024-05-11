package com.example.learncode.repository

import com.example.learncode.model.Account
import com.example.learncode.model.AccountInfoChangeEmailAttempt
import com.example.learncode.model.AccountInfoChangeEmailRequest
import com.example.learncode.model.AccountInfoChangePhoneAttempt
import com.example.learncode.model.AccountInfoChangePhoneRequest
import com.example.learncode.model.ResponseFromServer
import com.example.learncode.model.RetrofitInstance
import retrofit2.Response

class AccountRepository {
    private val accountInfoService = RetrofitInstance.apiService
    suspend fun registerAccount(username: String, password: String): Response<ResponseFromServer> {
        return accountInfoService.registerAccount(Account(username, password))
    }

    suspend fun requestChangeAccountEmail(
        username: String, email: String
    ): Response<ResponseFromServer> {
        return accountInfoService.requestChangeAccountEmail(
            AccountInfoChangeEmailRequest(
                username, email
            )
        )
    }

    suspend fun requestChangeAccountPhone(
        username: String, phone: String
    ): Response<ResponseFromServer> {
        return accountInfoService.requestChangeAccountPhone(
            AccountInfoChangePhoneRequest(
                username, phone
            )
        )
    }

    suspend fun attemptChangeAccountEmail(
        username: String, verifyCode: String
    ): Response<ResponseFromServer> {
        return accountInfoService.attemptChangeAccountEmail(
            AccountInfoChangeEmailAttempt(
                username, verifyCode
            )
        )
    }

    suspend fun attemptChangeAccountPhone(
        username: String, verifyCode: String
    ): Response<ResponseFromServer> {
        return accountInfoService.attemptChangeAccountPhone(
            AccountInfoChangePhoneAttempt(
                username, verifyCode
            )
        )
    }

}