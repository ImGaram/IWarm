package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName

data class WeatherWindResponse(
    @SerializedName("speed") val speed: Double     // 풍속
)
