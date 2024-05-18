package com.doansgu.cafectm.model

import com.google.gson.annotations.SerializedName

data class HomeScreen2Products(
    @SerializedName("banners") val banners: List<Product2>? = null,
    @SerializedName("bestSellers") val bestSellers: List<Product2>? = null,
    @SerializedName("forYou") val forYou: List<Product2>? = null,
)