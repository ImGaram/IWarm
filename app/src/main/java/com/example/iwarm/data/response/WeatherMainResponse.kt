package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherMainResponse(
    @SerializedName("temp") val temp: Double,   // 현재 기온
    @SerializedName("feels_like") val feelsLike: Double,    // 체감 기온
    @SerializedName("humidity") val humidity: Int   // 습도
): Serializable
