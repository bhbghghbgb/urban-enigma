package com.example.cart.Models

import java.sql.Timestamp

class User {
    data class Commonuser(
        val name: String,
        val gender: String,
        val dateOfBirth: Timestamp,
        val phone: String,
        val Account: Account,
        val _id: String,
    )

    data class Customer(
        val _id: String,
        val commonuser: Commonuser,
        val qrCode: String,
        val position: String,
        val membershipPoint: Int
    )
}
