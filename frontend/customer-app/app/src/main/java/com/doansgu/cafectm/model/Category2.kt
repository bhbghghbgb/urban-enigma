package com.doansgu.cafectm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category2(
    @SerialName("_id") val id: String? = null,
    @SerialName("name") val name: String? = null,
)