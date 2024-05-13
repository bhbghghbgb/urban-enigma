package com.doansgu.cafectm.model

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/helloworld")
    suspend fun helloBackend(): Response<ResponseBody>

    @GET("/products")
    suspend fun getAllProducts(): Response<List<Products>>

    @GET("/products/lists/popular")
    suspend fun getProductList(): Response<List<Products>>

    @GET("/products/lists/popular/limit")
    suspend fun getProductListLimit(): Response<List<Products>>

    @GET("/products/search/{name}")
    suspend fun searchProductByName(@Path("name") name: String): Response<List<Products>>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<Products>

    @GET("/auth/customer")
    suspend fun testAuthorization(): Response<ResponseBody>

    @GET("/cart")
    suspend fun getCartOfUser(): Response<Cart>

    @POST("/cart/add-to-cart")
    suspend fun addToCart(
        @Body request: AddToCartRequest
    ): Response<ResponseFromServer>

    @PATCH("/cart/delete")
    suspend fun deleteProductOfCart(
        @Body request: AddToCartRequest
    ): Response<ResponseFromServer>

    @GET("/orders/not-yet-delivered")
    suspend fun getOrdersNotYetDelivered(): Response<List<Order>>

    @GET("/orders/delivered")
    suspend fun getOrdersDelivered(): Response<List<Order>>

    @GET("/user/infocustomer")
    suspend fun getInfoOfCustomer(): Response<UserClass.Customer>

    @GET("/orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: String
    ): Response<Order>

    // dang ki
    @POST("/account/add")
    suspend fun registerAccount(@Body account: Account): Response<ResponseFromServer>

    @POST("/accountinfo/email")
    suspend fun requestChangeAccountEmail(
        @Body request: AccountInfoChangeEmailRequest
    ): Response<ResponseFromServer>

    @POST("/accountinfo/phone")
    suspend fun requestChangeAccountPhone(
        @Body request: AccountInfoChangePhoneRequest
    ): Response<ResponseFromServer>

    @PATCH("/accountinfo/email")
    suspend fun attemptChangeAccountEmail(
        @Body request: AccountInfoChangeEmailAttempt
    ): Response<ResponseFromServer>

    @PATCH("/accountinfo/phone")
    suspend fun attemptChangeAccountPhone(
        @Body request: AccountInfoChangePhoneAttempt
    ): Response<ResponseFromServer>
}