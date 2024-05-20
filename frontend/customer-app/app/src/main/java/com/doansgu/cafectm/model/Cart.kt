package com.doansgu.cafectm.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Cart(
    val _id: String,
    val user: String,
    var total: Double,
    val products: List<ProductOfCart>
)

data class Order(
    @SerializedName("_id")
    val id: String,
    @SerializedName("user")
    val user: UserClass.Customer,
    @SerializedName("orderDateTime")
    val orderDateTime: Timestamp,
    @SerializedName("status")
    val status: String,
    @SerializedName("deliveryLocation")
    val deliveryLocation: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("discount")
    val discount: Double,
    @SerializedName("detailOrders")
    val detailOrders: List<DetailOrders>,
    @SerializedName("paymentMethod")
    val paymentMethod: String,
    @SerializedName("totalPrice")
    val totalPrice: Double
);

data class DetailOrders(
    @SerializedName("product")
    val product: Product2,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("_id")
    val id: String
)