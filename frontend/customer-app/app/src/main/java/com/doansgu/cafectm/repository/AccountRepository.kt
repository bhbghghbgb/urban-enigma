package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.Account
import com.doansgu.cafectm.model.AccountInfoChangeEmailAttempt
import com.doansgu.cafectm.model.AccountInfoChangeEmailRequest
import com.doansgu.cafectm.model.AccountInfoChangePhoneAttempt
import com.doansgu.cafectm.model.AccountInfoChangePhoneRequest
import com.doansgu.cafectm.model.ResponseFromServer
import com.doansgu.cafectm.model.RetrofitInstance
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