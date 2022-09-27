package com.badsha.weatherappcompose.feature.di

import com.badsha.weatherappcompose.feature.domain.usecase.WeatherInteractor
import com.badsha.weatherappcompose.feature.domain.usecase.WeatherUseCase
import com.badsha.weatherappcompose.feature.presentation.weatherScreen.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideWeatherUseCase(weatherInteractor: WeatherInteractor): WeatherUseCase
}

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Provides
    @Singleton
    fun provideWeatherViewModel(weatherUseCase: WeatherInteractor): WeatherViewModel{
        return WeatherViewModel(weatherUseCase)
    }
}
