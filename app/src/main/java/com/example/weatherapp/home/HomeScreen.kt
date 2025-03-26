package com.example.weatherapp.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import com.example.weatherapp.data.models.Item8
import com.example.weatherapp.utils.Response
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WeatherScreen(
    city: String,
    viewModel: HomeViewModel
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val futureState by viewModel.futureWeatherState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()

        when (weatherState) {
            is Response.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Response.Success -> {
                val weather = (weatherState as Response.Success).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { WeatherHeader(city) }
                    item { WeatherInfo(weather) }
                    item {
                        WeatherDetails(
                            humidity = "${weather.main.humidity}%",
                            windSpeed = "${weather.wind.speed} km/h",
                            pressure = "${weather.main.pressure} hPa",
                            clouds = "${weather.clouds.all}%"
                        )
                    }

                    when (futureState) {
                        is Response.Loading -> {
                            item {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                        is Response.Success -> {
                            val forecastData = (futureState as Response.Success).data
                            item {
                                WeatherForecast(forecastList = forecastData.list)
                            }
                            item {
                                FiveDaysForecast(fiveDaysForecast = forecastData)
                            }
                        }
                        is Response.Failure -> {
                            item {
                                Text(
                                    text = "Error: ${(futureState as Response.Failure).error.message}",
                                    color = Color.Red,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

            }

            is Response.Failure -> {
                Text(
                    text = "Error: ${(weatherState as Response.Failure).error.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}





@Composable
fun WeatherHeader(city: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = city,
                color = Color.White,
                fontSize = 20.sp
            )
        }

        IconButton(onClick = { /* TODO: Open menu */ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(35.dp),
                tint = Color.White
            )
        }
    }
}


@Composable
fun BackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.b4),
            contentDescription = "Weather Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(Color.Black.copy(alpha = 0.3f), Color.Black.copy(alpha = 0.9f))
//                    )
//                )
//        )
    }
}

//@Composable
//fun WeatherHeader() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                imageVector = Icons.Default.LocationOn,
//                contentDescription = "Location",
//                tint = Color.White
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = "Paris",
//                color = Color.White,
//                fontSize = 20.sp,
//               // fontWeight = FontWeight.Bold
//            )
//        }
//
//        IconButton(onClick = { /* TODO: Open menu */ }) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Menu",
//                modifier = Modifier.size(35.dp),
//                tint = Color.White
//            )
//        }
//    }
//}
@Composable
fun WeatherDetails(humidity: String, windSpeed: String, pressure: String, clouds: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(R.drawable.icon_humidity, "Humidity", humidity)
                WeatherDetailItem(R.drawable.wind_speed_icon, "Wind Speed", windSpeed)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherDetailItem(R.drawable.icon_pressure, "Pressure", pressure)
                WeatherDetailItem(R.drawable.cloud_icon, "Clouds", clouds)
            }
        }
    }
}
@Composable
fun WeatherDetailItem(icon: Int, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(32.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
    }
}


@Composable
fun WeatherInfo(weather: CurrentWeatherForecast) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            imageModel = { "https://openweathermap.org/img/wn/${weather.weather?.firstOrNull()?.icon}@2x.png" },
            modifier = Modifier.size(60.dp)
        )


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${weather.main.temp}°C",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = weather.weather?.firstOrNull()?.main ?: "Unknown",
            fontSize = 28.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Updated ${weather.dt}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}



@Composable
fun WeatherForecast(forecastList: List<Item8>) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(forecastList) { forecast ->
            val time = forecast.dt_txt.substringAfter(" ")
            val temperature = "${forecast.main.temp.toInt()}°"
            val iconUrl = "https://openweathermap.org/img/wn/${forecast.weather.firstOrNull()?.icon}@2x.png"

            WeatherDay(time = time, temperature = temperature, iconUrl = iconUrl)
        }
    }
}

@Composable
fun WeatherDay(time: String, temperature: String, iconUrl: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .height(120.dp)
            .padding(horizontal = 8.dp)
            .background(
                Color.Black.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
    ) {
        Text(text = time, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        GlideImage(
            imageModel = { iconUrl },
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = temperature,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}


data class DailyForecast(
    val day: String,
    val temperature: String,
    val iconRes: Int
)
@Composable
fun FiveDaysForecast(fiveDaysForecast: FiveDaysForecast) {
    val groupedForecasts = fiveDaysForecast.list.groupBy { it.dt_txt.substringBefore(" ") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupedForecasts.forEach { (date, forecasts) ->
                val forecast = forecasts.first()
                val dayLabel = date
                val temperature = "${forecast.main.temp.toInt()}°"
                val iconUrl = "https://openweathermap.org/img/wn/${forecast.weather.firstOrNull()?.icon}@2x.png"
                val description = forecast.weather.firstOrNull()?.main ?: "Unknown"

                WeatherItemView(
                    WeatherItem(
                        day = dayLabel,
                        icon = iconUrl,
                        temperature = temperature,
                        windSpeed = description
                    )
                )
            }
        }
    }
}


data class WeatherItem(
    val day: String,
    val icon: String,
    val temperature: String,
    val windSpeed: String
)

@Composable
fun WeatherItemView(item: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.day,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        GlideImage(
            imageModel = { item.icon },
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = item.temperature,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = item.windSpeed,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

