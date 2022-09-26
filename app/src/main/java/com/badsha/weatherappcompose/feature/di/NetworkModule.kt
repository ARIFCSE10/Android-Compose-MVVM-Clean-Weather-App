package com.badsha.weatherappcompose.feature.di

import com.badsha.weatherappcompose.feature.data.remote.API
import com.badsha.weatherappcompose.feature.data.remote.RemoteDataSource
import com.badsha.weatherappcompose.feature.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

