package com.doansgu.cafectm.model

data class ProductOfCart(
    val product: Product2,
    var amount: Int,
    val price: Double,
    val _id: String
)

data class AddToCartRequest(val productId: String)
