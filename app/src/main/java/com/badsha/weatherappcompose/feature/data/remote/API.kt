package com.badsha.weatherappcompose.feature.data.remote

import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface API {
    @GET("forecast/daily")
    suspend fun getWeatherData(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("key") key:String = API.KEY,
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://api.weatherbit.io/v2.0/"
        const val KEY = "c07824617cf147ed8967f888a2b7df49"
    }
}