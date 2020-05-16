package com.bsrakdg.mviarchitecture.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CustomRetrofitBuilder {
    private const val BASE_URL = "https://open-api.xyz/"

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    }

    val networkService: NetworkService by lazy {
        retrofitBuilder
            .build()
            .create(NetworkService::class.java)
    }
}