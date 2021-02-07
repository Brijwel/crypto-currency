package com.brijwel.cryptocurrency.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Brijwel on 06-02-2021.
 */
object APIClient {
    private const val BASE_URL = "https://api.coincap.io/v2/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }
    val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}