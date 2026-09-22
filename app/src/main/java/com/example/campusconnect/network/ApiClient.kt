package com.example.campusconnect.network

import android.content.Context
import com.example.campusconnect.auth.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:52184/"

    private lateinit var tokenManager: TokenManager

    fun initialize(context: Context) {
        tokenManager = TokenManager(context.applicationContext)
    }

    private val okHttpClient: OkHttpClient by lazy {

        OkHttpClient.Builder()
            .addInterceptor { chain ->

                val originalRequest = chain.request()

                val requestBuilder = originalRequest
                    .newBuilder()

                if (::tokenManager.isInitialized) {

                    val token = tokenManager.getToken()

                    if (!token.isNullOrBlank()) {
                        requestBuilder.addHeader(
                            "Authorization",
                            "Bearer $token"
                        )
                    }
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    val service: CampusApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CampusApiService::class.java)
    }
}