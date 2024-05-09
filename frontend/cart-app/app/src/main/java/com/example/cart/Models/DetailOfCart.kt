package com.example.cart.Models

data class DetailOfCart(
    val product: Product,
    var amount: Int,
    val price: Double,
    val _id: String
)
