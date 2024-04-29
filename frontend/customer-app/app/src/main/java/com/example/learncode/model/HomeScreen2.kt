package com.example.learncode.model

import kotlinx.serialization.SerialName

data class HomeScreen2(
    @SerialName("banners") val banners: List<Product2>? = null,
    @SerialName("best_sellers") val bestSellers: List<Product2>? = null,
    @SerialName("for_you") val forYou: List<Product2>? = null,
)