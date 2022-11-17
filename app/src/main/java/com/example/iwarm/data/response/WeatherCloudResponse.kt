package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName

data class WeatherCloudResponse(
    @SerializedName("all") val all: Int
)
