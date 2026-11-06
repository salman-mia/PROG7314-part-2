package com.example.campusconnect.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Creates the Retrofit connection to the CampusConnect backend.
 */
object ApiClient {

    /*
     * Replace this address when the backend group member provides
     * the final deployed HTTPS address.
     *
     * The address must end with a forward slash.
     */
    private const val BASE_URL =
        "https://example.com/"

    /**
     * Provides one reusable instance of CampusApiService.
     */
    val service: CampusApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(CampusApiService::class.java)
    }
}