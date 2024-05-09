package com.example.cart.Models

data class Product(
    val _id: String,
    val category: String,
    val description: String,
    val image: String,
    val name: String,
    val popular: Boolean,
    val price: Double,
    val rating: Double
)