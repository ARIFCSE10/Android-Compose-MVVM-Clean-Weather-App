package com.badsha.weatherappcompose.feature.presentation

sealed class Screen(val route: String) {
    object LocationScreen : Screen("locationScreen")
    object WeatherScreen : Screen("weatherScreen")
}