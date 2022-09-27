package com.badsha.weatherappcompose.feature.presentation.weatherScreen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherDayDTO
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalWeatherDayList(daysData: List<WeatherDayDTO>, onItemClick:(dayData:WeatherDayDTO)->Unit,modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState()
    var chunk:List<List<WeatherDayDTO>> = listOf()
    chunk = daysData.chunked(5)
    HorizontalPager(count = chunk.size, contentPadding = PaddingValues(8.dp)) { rowId ->
        LazyColumn(state = rememberLazyListState()){
            items(chunk[rowId].size){colId->
                WeatherCardSmall(dayData = chunk[rowId][colId], onClick = {
                    onItemClick(it)
                    },
                )
            }
        }
    }
}