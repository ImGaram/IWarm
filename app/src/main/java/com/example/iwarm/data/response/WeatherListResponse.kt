package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName

data class WeatherListResponse(
    @SerializedName("main") val main: WeatherMainResponse,   // 기온 리스트
    @SerializedName("weather") val weather: List<Weather2Response>, // 날씨 리스트
    @SerializedName("wind") val wind: WeatherWindResponse,   // 바람 리스트
    @SerializedName("clouds") val clouds: WeatherCloudResponse,
    @SerializedName("dt_txt") val dtTxt: String     // 날짜
)
