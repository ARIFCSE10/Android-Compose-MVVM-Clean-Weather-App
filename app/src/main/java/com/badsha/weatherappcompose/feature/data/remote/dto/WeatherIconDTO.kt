package com.badsha.weatherappcompose.feature.data.remote.dto

import com.google.gson.annotations.SerializedName


data class WeatherIconDTO (

    @SerializedName("icon"        ) var icon        : String? = null,
    @SerializedName("code"        ) var code        : String? = null,
    @SerializedName("description" ) var description : String? = null

)