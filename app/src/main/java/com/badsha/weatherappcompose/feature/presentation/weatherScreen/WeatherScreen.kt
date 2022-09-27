package com.badsha.weatherappcompose.feature.presentation.weatherScreen

import SimpleBottomSheetWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.badsha.weatherappcompose.feature.presentation.weatherScreen.component.HorizontalWeatherDayList
import com.badsha.weatherappcompose.feature.presentation.weatherScreen.component.WeatherCardLarge
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
            ,
        ) {
            if (state.isLoading) { // Loading Case
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = "Collecting Weather Info",
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.primary
                    )
                }

            } else if (state.isFailed) {  // Error Case
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        state.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.h5
                    )
                }
            } else {  // Success Case
                val todayData = viewModel.weatherData.value.data.first()
                val city = viewModel.weatherData.value.cityName ?: "--"
                val allDayData = viewModel.weatherData.value.data
                SimpleBottomSheetWrapper(
                    content = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            WeatherCardLarge(city, todayData)
                            HorizontalWeatherDayList(allDayData.subList(1, 16), onItemClick = {
                                viewModel.selectedDay.value = it
                                viewModel.bottomSheetScope?.launch {
                                    if (viewModel.bottomSheetState?.isVisible == true) {
                                        viewModel.bottomSheetState?.hide()
                                        viewModel.bottomSheetState?.hide()
                                    } else {
                                        viewModel.bottomSheetState?.show()
                                    }
                                }
                            })
                        }
                    },
                    viewModel,
                ) {
                    WeatherCardLarge(city = city, dayData = viewModel.selectedDay.value ?: todayData)
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ComposablePreview() {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = "Collecting Location Info",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primary
            )
        }

}