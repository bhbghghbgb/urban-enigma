package com.example.delivery_app.model

import com.example.delivery_app.AUTHORIZATION_HEADER
import com.example.delivery_app.AUTHORIZATION_SCHEME
import com.example.delivery_app.BACKEND_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().client(OkHttpClient.Builder().addInterceptor { chain ->
            val authorization = AuthorizationManager.authorization
            chain.proceed(
                if (authorization === null) chain.request() else chain.request().newBuilder()
                    .addHeader(
                        AUTHORIZATION_HEADER, "$AUTHORIZATION_SCHEME $authorization"
                    ).build()
            )
        }.build()).baseUrl(BACKEND_BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}