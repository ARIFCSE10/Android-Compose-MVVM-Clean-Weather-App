package com.badsha.weatherappcompose.feature.domain.usecase

import com.badsha.weatherappcompose.feature.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherInteractor @Inject constructor(private val weatherRepository: IWeatherRepository):
    WeatherUseCase {
    override fun getWeatherData(lat: Double, lon: Double) = weatherRepository.getWeatherData(lat, lon)
}