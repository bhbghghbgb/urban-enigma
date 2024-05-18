package com.doansgu.cafectm.model

import com.google.gson.annotations.SerializedName

data class Product2(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("popular") val popular: Boolean? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("avgRating") val rating: Double? = null,
//    @SerializedName("comment_count") val commentCount: Int? = null,
//    @SerializedName("rating_count") val ratingCount: Int? = null,
    @SerializedName("category") val category: Category2? = null,
)