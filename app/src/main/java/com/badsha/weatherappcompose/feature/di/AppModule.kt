package com.badsha.weatherappcompose.feature.di

import android.location.Location
import com.badsha.weatherappcompose.feature.data.remote.API
import com.badsha.weatherappcompose.feature.data.remote.RemoteDataSource
import com.badsha.weatherappcompose.feature.data.repository.WeatherRepository
import com.badsha.weatherappcompose.feature.domain.repository.IWeatherRepository
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(remoteDataSource: RemoteDataSource): WeatherRepository {
        return WeatherRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideApi(): API {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}


@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideWeatherUseCase(weatherInteractor: WeatherInteractor): WeatherUseCase
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(weatherRepository: WeatherRepository): IWeatherRepository
}