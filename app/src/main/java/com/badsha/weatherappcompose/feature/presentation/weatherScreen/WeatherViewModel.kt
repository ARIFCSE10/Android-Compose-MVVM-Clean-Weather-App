package com.badsha.weatherappcompose.feature.presentation.weatherScreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.badsha.weatherappcompose.feature.data.Resource
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherDayDTO
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherResponse
import com.badsha.weatherappcompose.feature.domain.usecase.WeatherInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherInteractor,
) : ViewModel(){
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    var selectedDay: MutableState<WeatherDayDTO?> = mutableStateOf(null)

    val weatherData = mutableStateOf(WeatherResponse())
    @OptIn(ExperimentalMaterialApi::class)
    var bottomSheetState : ModalBottomSheetState? = null
    var bottomSheetScope : CoroutineScope? = null

    private var lat: Double? = null
    private var lon: Double? = null

    fun setLocationInfo(lat:Double?, lon:Double?){
        this.lat = lat
        this.lon = lon
    }

    /// Collect weather data
    fun getWeatherData(){
        if(state.value.isDataLoaded) return

        viewModelScope.launch {
            weatherUseCase.getWeatherData(lat ?: 0.0, lon?:0.0).collect {resource ->
                when(resource){
                    is Resource.Success -> {
                        resource.data?.let { weatherData.value = it }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isDataLoaded = true,
                            isFailed = false,
                        )
                    }
                    is Resource.Error -> _state.value = _state.value.copy(
                        isLoading = false,
                        isFailed = true,
                        isDataLoaded = false,
                        errorMessage = resource.message ?: ""
                    )
                    is Resource.Loading ->_state.value = _state.value.copy(
                        isLoading = true,
                        isDataLoaded = false,
                        isFailed = false,
                        )
                }
            }
        }
    }
}
