package com.badsha.weatherappcompose.feature.data.remote.dto

import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherDayDTO
import com.google.gson.annotations.SerializedName


data class WeatherResponse (

    @SerializedName("city_name"    ) var cityName    : String?         = null,
    @SerializedName("state_code"   ) var stateCode   : String?         = null,
    @SerializedName("country_code" ) var countryCode : String?         = null,
    @SerializedName("lat"          ) var lat         : String?         = null,
    @SerializedName("lon"          ) var lon         : String?         = null,
    @SerializedName("timezone"     ) var timezone    : String?         = null,
    @SerializedName("data"         ) var data        : ArrayList<WeatherDayDTO> = arrayListOf()

)