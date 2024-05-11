package com.doansgu.cafectm.model

data class Account(
    val username: String,
    val password: String,
)

data class AccountDetailed(
    val username: String,
    val password: String,
    val email: String?,
    val emailVerified: Boolean?,
    val phone: String?,
    val phoneVerified: Boolean?
)