package com.example.iwarm.repository

import android.app.Application
import com.example.iwarm.data.response.WeatherResponse
import com.example.iwarm.network.WeatherObject

class WeatherRepository(application: Application) {
    companion object {
        private var instance: WeatherRepository? = null

        fun getInstance(application: Application): WeatherRepository? {
            if (instance==null) instance = WeatherRepository(application)
            return instance
        }
    }

    suspend fun getWeather(lat: Double, lot: Double, key: String): WeatherResponse {
        val response = WeatherObject.getWeatherService.getWeather(lat, lot, key)
        return if (response.isSuccessful)
            response.body() as WeatherResponse
        else
            WeatherResponse(401, response.message(), null, null)
    }
}