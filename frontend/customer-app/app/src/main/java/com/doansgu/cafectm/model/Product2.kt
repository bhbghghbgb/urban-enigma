package com.doansgu.cafectm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product2(
    @SerialName("_id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("popular") val popular: Boolean? = null,
    @SerialName("image") val image: String? = null,
//    @SerialName("rating") val rating: Double? = null,
//    @SerialName("comment_count") val commentCount: Int? = null,
//    @SerialName("rating_count") val ratingCount: Int? = null,
    @SerialName("category") val category: Category2? = null,
)