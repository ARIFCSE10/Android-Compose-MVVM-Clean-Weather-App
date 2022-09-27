package com.badsha.weatherappcompose.feature.presentation.weatherScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherDayDTO
import com.badsha.weatherappcompose.feature.util.DrawableUtil


@Composable
fun WeatherCardLarge(city:String, dayData:WeatherDayDTO, modifier: Modifier = Modifier) {
    return Card(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
        elevation = 8.dp, shape = RoundedCornerShape(8.dp), backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.secondaryVariant.copy(alpha = 0.1f), shape = MaterialTheme.shapes.large)
            .padding(8.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(city, style = MaterialTheme.typography.h5)
                Text("${dayData.temp}°C", style = MaterialTheme.typography.h5)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(dayData.datetime?.toString() ?: "--"  ,textAlign = TextAlign.Start, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.secondary, modifier = Modifier.weight(1f))
                Text(dayData.weather?.description ?: "--", textAlign = TextAlign.End,  style = MaterialTheme.typography.h6, color = MaterialTheme.colors.secondary, modifier = Modifier.weight(1f))
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                , horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = DrawableUtil.getDrawableId(dayData.weather?.icon ?: "", context = LocalContext.current)),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(100.dp)
                        .background(Color.White, CircleShape)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("min:${dayData.minTemp}°C", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primaryVariant)
                Text("max:${dayData.maxTemp}°C", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primaryVariant)
            }
        }
    }
}