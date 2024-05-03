package com.example.learncode.model

data class ProductOfCart(
    val product: Products,
    val amount: Int,
    val price: Double,
    val _id: String
)

data class AddToCartRequest(val productId: String)
