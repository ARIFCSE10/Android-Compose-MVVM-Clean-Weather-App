package com.badsha.weatherappcompose.feature.presentation.weatherScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.badsha.weatherappcompose.feature.data.remote.dto.WeatherDayDTO
import com.badsha.weatherappcompose.feature.util.DrawableUtil

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherCardSmall(dayData:WeatherDayDTO, onClick:(dayData:WeatherDayDTO)->Unit, modifier: Modifier = Modifier){
    return Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        onClick = {
          onClick.invoke(dayData)
        },
        elevation = 2.dp, shape = RoundedCornerShape(8.dp), backgroundColor = MaterialTheme.colors.background) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(8.dp)){
            Column(verticalArrangement = Arrangement.Center) {
                Text(dayData.datetime.toString(), style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primary)
                Text(dayData.weather?.description ?: "--", style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            if(dayData.weather?.icon?.isNotEmpty() == true){
                Image(
                    painter = painterResource(id = DrawableUtil.getDrawableId(dayData.weather?.icon ?: "", context = LocalContext.current)),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(64.dp)
                        .background(Color.White, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text("Current : ${dayData.temp}°C", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                Text("Max : ${dayData.maxTemp}°C", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
                Text("Min : ${dayData.minTemp}°C", style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground)
            }
        }
    }
}