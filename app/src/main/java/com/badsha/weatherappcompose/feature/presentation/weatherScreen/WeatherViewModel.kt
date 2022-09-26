package com.badsha.weatherappcompose.feature.presentation.weatherScreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgument
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
    savedStateHandle: SavedStateHandle,
) : ViewModel(){
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    var selectedDay: MutableState<WeatherDayDTO?> = mutableStateOf(null)

    val weatherData = mutableStateOf(WeatherResponse())
    @OptIn(ExperimentalMaterialApi::class)
    var bottomSheetState : ModalBottomSheetState? = null
    var bottomSheetScope : CoroutineScope? = null

    private var lat: Double? = checkNotNull(savedStateHandle.get<Float>("lat")).toDouble()
    private var lon: Double? = checkNotNull(savedStateHandle.get<Float>("lat")).toDouble()

    init {
        viewModelScope.launch {
            getWeatherData(lat, lon)
        }
    }

    private suspend fun getWeatherData(lat:Double?, lon:Double?){
        weatherUseCase.getWeatherData(lat ?: 0.0, lon?:0.0).collect {resource ->
            when(resource){
                is Resource.Success -> {
                    resource.data?.let { weatherData.value = it }
                    _state.value = _state.value.copy(
                        isLoading = false,
                    )
                }
                is Resource.Error -> _state.value = _state.value.copy(
                    isLoading = false,
                    isFailed = true,
                    errorMessage = resource.message ?: ""
                )
                is Resource.Loading ->_state.value = _state.value.copy(
                    isLoading = true,
                )
            }
        }
    }
}
