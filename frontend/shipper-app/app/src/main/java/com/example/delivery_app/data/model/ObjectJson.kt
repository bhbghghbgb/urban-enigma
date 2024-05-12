package com.example.delivery_app.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Account(
    val username: String,
    val password: String,
)

data class AuthResult(
    val message: String,
    val isValid: Boolean
)

data class Token(
    val token: String,
    val roleOfAccount: String,
)

data class Commonuser(
    val name: String,
    val gender: String,
    val dateOfBirth: Timestamp,
    val phone: String,
    val account: Account,
    val _id: String,
)

data class Customer(
    val _id: String,
    val commonuser: Commonuser,
    val qrCode: String,
    val membershipPoint: Int
)

data class Staff(
    val _id: String,
    val commonuser: Commonuser,
    val address: String,
    val position: String,
)


data class Order(
    @SerializedName("_id")
    val id: String,
    @SerializedName("user")
    val user: Customer,
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
    val paymentMethod: String
);

data class DetailOrders(
    @SerializedName("product")
    val product: Product,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("_id")
    val id: String
)

data class Product(
    val _id: String,
    val name: String,
    val image: String,
    val description: String,
    val price: Double,
    val popular: Boolean,
    val category: Category,
    val avgRating: Double
)

data class Category(
    val _id: String,
    val name: String,
)

data class ResponseFromServer(
    val message: String
)