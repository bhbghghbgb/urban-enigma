package com.example.cart.Models

data class Cart(
    val _id: String,
    val customer: String,
    val products: List<DetailOfCart>,
    var total: Double
)




