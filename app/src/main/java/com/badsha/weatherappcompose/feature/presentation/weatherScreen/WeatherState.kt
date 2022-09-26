package com.badsha.weatherappcompose.feature.presentation.weatherScreen

data class WeatherState(
    var isLoading: Boolean = true,
    var isFailed: Boolean = false,
    var errorMessage: String = ""
)
