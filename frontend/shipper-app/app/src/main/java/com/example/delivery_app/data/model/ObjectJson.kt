package com.example.delivery_app.data.model

import java.sql.Timestamp

data class Product (
    val id: Int,
    val image: Int,
    val title: String,
    val des: String,
    val price: String,
    val star: Double
)

data class Account(
    val username: String,
    val password: String,
)

data class AuthResult(
    val message: String,
    val isValid:Boolean
)

data class Token(
    val token: String,
    val roleOfAccount: String,
)

data class Commonuser(
    val name: String,
    val gender: String,
    val dateOfBirth: Timestamp,
    val phone: String,
    val account: Account,
    val _id: String,
)

data class Customer(
    val _id: String,
    val commonuser: Commonuser,
    val qrCode: String,
    val membershipPoint: Int
)

data class Staff(
    val _id: String,
    val commonuser: Commonuser,
    val address: String,
    val position: String,
)