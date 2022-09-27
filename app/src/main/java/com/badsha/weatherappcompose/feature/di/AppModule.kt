package com.badsha.weatherappcompose.feature.di

import com.badsha.weatherappcompose.feature.domain.usecase.WeatherInteractor
import com.badsha.weatherappcompose.feature.domain.usecase.WeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideWeatherUseCase(weatherInteractor: WeatherInteractor): WeatherUseCase
}
