package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherCloudResponse(
    @SerializedName("all") val all: Int
): Serializable
