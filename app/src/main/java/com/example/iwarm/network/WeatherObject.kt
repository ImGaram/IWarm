package com.example.iwarm.network

import com.example.iwarm.data.ApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherObject {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val weatherRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getWeatherService: WeatherService by lazy {
        weatherRetrofit.create(WeatherService::class.java)
    }
}