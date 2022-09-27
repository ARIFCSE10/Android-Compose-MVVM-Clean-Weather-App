package com.badsha.weatherappcompose.feature.data.repository

import com.badsha.weatherappcompose.feature.data.Resource
import com.badsha.weatherappcompose.feature.data.remote.ApiResponse
import com.badsha.weatherappcompose.feature.data.remote.RemoteDataSource
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherResponse
import com.badsha.weatherappcompose.feature.domain.repository.IWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : IWeatherRepository {
    override fun getWeatherData(lat: Double, lon: Double): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())
        try {
            when (val apiResponse = remoteDataSource.getWeatherData(lat,lon).first()) {
                is ApiResponse.Success -> {
                    emit(Resource.Success(apiResponse.data) )
                }
                is ApiResponse.Empty -> emit(Resource.Error("Empty Data"))
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.message ?: ""))
        }
    }

}
