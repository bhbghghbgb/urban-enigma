package com.doansgu.cafectm.model

data class Products(
    val _id: String,
    val name: String,
    val image: String,
    val description: String,
    val price: Double,
    val popular: Boolean,
    val category: Category2?,
    val avgRating: Double
)