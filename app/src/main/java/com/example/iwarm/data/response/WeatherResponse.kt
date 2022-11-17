package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("cod") val cod: Int,
    @SerializedName("message") val message: String,
    @SerializedName("cnt") val cnt: Int?,
    @SerializedName("list") val list: List<WeatherListResponse>?
)
