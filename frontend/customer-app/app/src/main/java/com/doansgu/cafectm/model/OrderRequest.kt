package com.doansgu.cafectm.model

data class OrderRequest(
    val products: List<ProductOfCart>,
    val discount: Int,
    val deliveryLocation : String,
    val note: String,
    val payment: String,
)
