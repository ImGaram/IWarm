package com.example.iwarm.network

import com.example.iwarm.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lot: Double,
        @Query("appid") key: String
    ): Response<WeatherResponse>
}