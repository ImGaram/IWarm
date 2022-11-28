package com.example.iwarm.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather2Response(
    @SerializedName("main") val main: String,      // 날씨
    @SerializedName("description") val description: String // 날씨 세부정보
): Serializable
