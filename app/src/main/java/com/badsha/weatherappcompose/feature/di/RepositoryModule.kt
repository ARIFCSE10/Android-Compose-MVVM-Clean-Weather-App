package com.badsha.weatherappcompose.feature.di

import com.badsha.weatherappcompose.feature.data.repository.WeatherRepository
import com.badsha.weatherappcompose.feature.domain.repository.IWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(weatherRepository: WeatherRepository): IWeatherRepository
}