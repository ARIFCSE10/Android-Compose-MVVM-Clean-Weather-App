package com.badsha.weatherappcompose.feature.domain.repository

import com.badsha.weatherappcompose.feature.data.Resource
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    fun getWeatherData(lat: Double, lon: Double): Flow<Resource<WeatherResponse>>
}