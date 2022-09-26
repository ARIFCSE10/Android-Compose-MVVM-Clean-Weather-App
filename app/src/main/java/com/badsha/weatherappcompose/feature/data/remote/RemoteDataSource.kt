package com.badsha.weatherappcompose.feature.data.remote

import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val service: API) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: API): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    suspend fun getWeatherData(lat: Double, lon: Double): Flow<ApiResponse<WeatherResponse>> {
        return flow {
            try {
                val response = service.getWeatherData(lat, lon)
                if (response.data.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Error("Empty Data"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}