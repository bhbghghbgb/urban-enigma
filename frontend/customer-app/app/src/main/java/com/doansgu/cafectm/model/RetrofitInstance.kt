package com.doansgu.cafectm.model

import android.util.Log
import com.doansgu.cafectm.AUTHORIZATION_HEADER
import com.doansgu.cafectm.AUTHORIZATION_SCHEME
import com.doansgu.cafectm.BACKEND_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().client(OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
            // add authorization interceptor
            .addInterceptor { chain ->
                val authorization = AuthorizationManager.getAuthorization()
                chain.proceed(
                    if (authorization === null) chain.request() else chain.request().newBuilder()
                        .addHeader(
                            AUTHORIZATION_HEADER, "$AUTHORIZATION_SCHEME $authorization"
                        ).build()
                )
                // catch exception interceptor
            }.addInterceptor { chain ->
                try {
                    return@addInterceptor chain.proceed(chain.request())
                } catch (e: Exception) {
                    e.printStackTrace()
                    var msg = ""
                    when (e) {
                        is SocketTimeoutException -> {
                            msg = "Timeout - Please check your internet connection"
                        }

                        is UnknownHostException -> {
                            msg = "Unable to make a connection. Please check your internet"
                        }

                        is ConnectionShutdownException -> {
                            msg = "Connection shutdown. Please check your internet"
                        }

                        is IOException -> {
                            msg = "Server is unreachable, please try again later."
                        }

                        is IllegalStateException -> {
                            msg = "IllegalStateException - ${e.message}"
                        }

                        else -> {
                            msg = "Unknown Error - ${e.message}"
                        }
                    }
                    Log.e("RetrofitInstance", "${e.message} - msg")
                    return@addInterceptor Response.Builder().code(999) // Set appropriate error code
                        .protocol(Protocol.HTTP_1_1).message(msg).request(chain.request())
                        .body("{${e}}".toResponseBody(null)).build()
                }
            }.build()
        ).baseUrl(BACKEND_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}