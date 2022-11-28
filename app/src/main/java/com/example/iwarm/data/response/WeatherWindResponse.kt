package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherWindResponse(
    @SerializedName("speed") val speed: Double     // 풍속
): Serializable
