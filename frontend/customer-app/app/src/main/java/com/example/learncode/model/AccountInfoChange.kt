package com.example.learncode.model

data class AccountInfoChangeEmailRequest(
    val username: String, val email: String, val infoType: String = "email"
)

data class AccountInfoChangePhoneRequest(
    val username: String, val phone: String, val infoType: String = "phone"
)

data class AccountInfoChangeEmailAttempt(
    val username: String, val verifyCode: String, val infoType: String = "email"
)

data class AccountInfoChangePhoneAttempt(
    val username: String, val verifyCode: String, val infoType: String = "phone"
)