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

@Composable
fun WeatherScreen(city: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherHeader(city)
            Spacer(modifier = Modifier.height(20.dp))
            WeatherInfo()
            Spacer(modifier = Modifier.height(20.dp))
            WeatherForecast()
            Spacer(modifier = Modifier.height(20.dp))
            FiveDaysForecast()
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
fun WeatherInfo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(
//            text = "June 07",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
        Icon(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "Weather Icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "24°C",
            fontSize =40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Clear",
            fontSize = 28.sp,
           // fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Updated 6/7/2023 4:55 PM",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White//.copy(alpha = 0.7f)
        )


    }
}

@Composable
fun WeatherForecast() {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            listOf(
                DailyForecast("Now", "22°", R.drawable.sun),
                DailyForecast("15:00", "20°", R.drawable.wind_speed_icon),
                DailyForecast("16:00", "21°", R.drawable.sun),
                DailyForecast("17:00", "18°", R.drawable.icon_pressure),
                DailyForecast("18:00", "21°", R.drawable.wind_speed_icon),
                DailyForecast("19:00", "18°", R.drawable.icon_pressure),
                DailyForecast("20:00", "21°", R.drawable.wind_speed_icon),
                DailyForecast("21:00", "18°", R.drawable.icon_pressure)
            )
        ) { forecast ->
            WeatherDay(forecast)
        }
    }
}

@Composable
fun WeatherDay(forecast: DailyForecast) {
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
        Text(text = forecast.day, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = forecast.iconRes),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = forecast.temperature,
            color = Color.White,
            fontSize = 20.sp,
            //fontWeight = FontWeight.Bold
        )
    }
}

data class DailyForecast(
    val day: String,
    val temperature: String,
    val iconRes: Int
)
@Composable
fun FiveDaysForecast() {
    val forecastData = listOf(
        WeatherItem("Wed 16", R.drawable.sun, "Sunny", "22°"),
        WeatherItem("Thu 17", R.drawable.icon_humidity, "Windy", "25°"),
        WeatherItem("Fri 18", R.drawable.sun, "Sunny", "40°"),
        WeatherItem("Sat 19", R.drawable.wind_speed_icon, "Windy", "25°"),
        WeatherItem("Sun 20", R.drawable.sun, "Sunny", "30°")
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(forecastData) { item ->
                WeatherItemView(item)
            }
        }
    }
}


@Composable
fun WeatherItemView(item: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.day, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(32.dp)
        )

        Text(text = item.temperature, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Text(text = item.windSpeed, color = Color.White, fontSize = 16.sp)
    }
}


data class WeatherItem(val day: String, val icon: Int, val temperature: String, val windSpeed: String)

//@Preview(showBackground = true)
//@Composable
//fun PreviewWeatherScreen() {
//    WeatherScreen()
//}
//package com.example.weatherapp.home
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.AssistChipDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.weatherapp.R
////@Preview(showSystemUi = true)
////@Composable
////fun HomeScreen(){
////    val context= LocalContext.current
////    Scaffold (){ innerPadding -> Column (modifier = Modifier.padding(innerPadding).background(Color.Blue).fillMaxSize()){
////        Icon(
////            painter = painterResource(id = R.drawable.menu_icon),
////            contentDescription = "location description",
////           // modifier = Modifier.size(AssistChipDefaults.IconSize),
////            tint = colorResource(R.color.white) ,
////        )
////    } }
////}
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun MyScreen() {
////    Scaffold(
////        topBar = {
////            TopAppBar(
////                title = {
////                    Row(
////                        verticalAlignment = Alignment.CenterVertically
////                    ) {
////                        Icon(
////                            painter = painterResource(id = R.drawable.icon_humidity),
////                            contentDescription = "Menu Icon",
////                            modifier = Modifier.size(24.dp)
////                        )
////                        Spacer(modifier = Modifier.width(8.dp))
////                        Text(
////                            text = "My App",
////                            fontSize = 18.sp
////                        )
////                    }
////                },
////                actions = {
////                    IconButton(onClick = { /* TODO: Handle click */ }) {
////                        Icon(
////                            painter = painterResource(id = R.drawable.menu_icon),
////                            contentDescription = "Settings Icon"
////                        )
////                    }
////                }
////            )
////        }
////    ) { innerPadding ->
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(innerPadding),
////            verticalArrangement = Arrangement.Center,
////            horizontalAlignment = Alignment.CenterHorizontally
////        ) {
////            Text(text = "Hello, Jetpack Compose!")
////        }
////    }
////}
////
////@Preview(showSystemUi = true)
////@Composable
////fun PreviewMyScreen() {
////    MyScreen()
////}
//
//
//
//
//
//
//
//
//
//
//
//
//@Composable
//fun WeatherScreen() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.b5),
//            contentDescription = "Weather Background",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            WeatherHeader()
//            Spacer(modifier = Modifier.height(20.dp))
//            WeatherInfo()
//            Spacer(modifier = Modifier.height(20.dp))
//            WeatherForecast()
//        }
//    }
//}
//
//@Composable
//fun WeatherHeader() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
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
//                fontWeight = FontWeight.Bold
//            )
//        }
//
//        IconButton(onClick = { /* TODO: Open menu */ }) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Menu",
//                tint = Color.White
//            )
//        }
//    }
//}
//
//@Composable
//fun WeatherInfo() {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(
//            text = "June 07",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//        Text(
//            text = "Updated 6/7/2023 4:55 PM",
//            fontSize = 14.sp,
//            color = Color.White.copy(alpha = 0.7f)
//        )
//        Spacer(modifier = Modifier.height(12.dp))
//        Icon(
//            painter = painterResource(id = R.drawable.icon_humidity),
//            contentDescription = "Weather Icon",
//            tint = Color.White,
//            modifier = Modifier.size(60.dp)
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "Clear",
//            fontSize = 24.sp,
//            color = Color.White
//        )
//        Text(
//            text = "24°C",
//            fontSize = 50.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            WeatherDetail("Humidity", "56%")
//            WeatherDetail("Wind", "4.63 km/h")
//            WeatherDetail("Feels Like", "22°")
//        }
//    }
//}
//
//@Composable
//fun WeatherDetail(title: String, value: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
//            .padding(8.dp)
//    ) {
//        Text(text = title, color = Color.White, fontSize = 14.sp)
//        Text(
//            text = value,
//            color = Color.White,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun WeatherForecast() {
//    LazyRow(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        items(listOf(
//            DailyForecast("Wed 16", "22°", R.drawable.icon_pressure),
//            DailyForecast("Thu 17", "25°", R.drawable.icon_humidity),
//            DailyForecast("Fri 18", "23°", R.drawable.wind_speed_icon),
//            DailyForecast("Sat 19", "25°", R.drawable.icon_pressure)
//        )) { forecast ->
//            WeatherDay(forecast)
//        }
//    }
//}
//
//@Composable
//fun WeatherDay(forecast: DailyForecast) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .padding(horizontal = 8.dp)
//            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(12.dp))
//            .padding(8.dp)
//    ) {
//        Text(text = forecast.day, color = Color.White, fontSize = 14.sp)
//        Icon(
//            painter = painterResource(id = forecast.iconRes),
//            contentDescription = "Weather Icon",
//            tint = Color.White,
//            modifier = Modifier.size(24.dp)
//        )
//        Text(
//            text = forecast.temperature,
//            color = Color.White,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//data class DailyForecast(
//    val day: String,
//    val temperature: String,
//    val iconRes: Int
//)
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewWeatherScreen() {
//    WeatherScreen()
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////@Composable
////fun WeatherScreen(
////    cityName: String = "Paris",
////    currentDate: String = "June 07",
////    lastUpdated: String = "Updated 6/7/2023 4:55 PM",
////    weatherDescription: String = "Clear",
////    temperature: Int = 24,
////    humidity: Int = 56,
////    windSpeed: Double = 6.63,
////    feelsLike: Int = 22,
////    dailyForecasts: List<DailyForecast> = listOf(
////        DailyForecast("Wed 16", 24, "sunny"),
////        DailyForecast("Thu 17", 27, "sunny"),
////        DailyForecast("Fri 18", 25, "cloudy"),
////        DailyForecast("Sat 19", 22, "rainy"),
////    )
////) {
////    Box(
////        modifier = Modifier.fillMaxSize()
////    ) {
////        // Background image (replace with your own approach, e.g. Coil)
////        Image(
////            painter = painterResource(id = R.drawable.sky_background),
////            contentDescription = "Background Image",
////            contentScale = ContentScale.Crop,
////            modifier = Modifier.fillMaxSize()
////        )
////
////        TopBarOverlay(cityName = cityName)
////
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(horizontal = 16.dp, vertical = 16.dp),
////            verticalArrangement = Arrangement.Center,
////            horizontalAlignment = Alignment.CenterHorizontally
////        ) {
////            Text(
////                text = currentDate,
////                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
////            )
////            Text(
////                text = lastUpdated,
////                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////            )
////
////            Spacer(modifier = Modifier.height(24.dp))
////
////            // Weather icon & temperature
////            Icon(
////                painter = painterResource(id = R.drawable.icon_pressure),
////                contentDescription = "Weather Icon",
////                tint = Color.Yellow,
////                modifier = Modifier.size(48.dp)
////            )
////
////            Text(
////                text = "$temperature°C",
////                style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
////            )
////            Text(
////                text = weatherDescription,
////                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
////            )
////
////            Spacer(modifier = Modifier.height(24.dp))
////
////            // Weather details: humidity, wind, feels like
////            WeatherDetailsRow(humidity, windSpeed, feelsLike)
////
////            Spacer(modifier = Modifier.height(24.dp))
////
////            // Forecast Row
////            DailyForecastRow(dailyForecasts)
////        }
////    }
////}
////
////@Composable
////fun TopBarOverlay(cityName: String) {
////    Box(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(top = 40.dp, bottom = 8.dp)
////            .height(56.dp),
////        contentAlignment = Alignment.Center
////    ) {
////        Text(
////            text = cityName,
////            style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
////        )
////        // Add icons if needed
////    }
////}
////
////@Composable
////fun WeatherDetailsRow(humidity: Int, windSpeed: Double, feelsLike: Int) {
////    Row(
////        modifier = Modifier.fillMaxWidth(),
////        horizontalArrangement = Arrangement.SpaceEvenly,
////        verticalAlignment = Alignment.CenterVertically
////    ) {
////        Column(horizontalAlignment = Alignment.CenterHorizontally) {
////            Icon(
////                painter = painterResource(id = R.drawable.icon_humidity),
////                contentDescription = "Humidity Icon",
////                tint = Color.White,
////                modifier = Modifier.size(24.dp)
////            )
////            Text(
////                text = "$humidity%",
////                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////            )
////        }
////
////        Column(horizontalAlignment = Alignment.CenterHorizontally) {
////            Icon(
////                painter = painterResource(id = R.drawable.icon_pressure),
////                contentDescription = "Wind Icon",
////                tint = Color.White,
////                modifier = Modifier.size(24.dp)
////            )
////            Text(
////                text = "${windSpeed}km/h",
////                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////            )
////        }
////
////        Column(horizontalAlignment = Alignment.CenterHorizontally) {
////            Icon(
////                painter = painterResource(id = R.drawable.wind_speed_icon),
////                contentDescription = "Feels Like Icon",
////                tint = Color.White,
////                modifier = Modifier.size(24.dp)
////            )
////            Text(
////                text = "$feelsLike°",
////                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////            )
////        }
////    }
////}
////
////@Composable
////fun DailyForecastRow(dailyForecasts: List<DailyForecast>) {
////    Row(
////        modifier = Modifier.fillMaxWidth(),
////        horizontalArrangement = Arrangement.SpaceEvenly
////    ) {
////        dailyForecasts.forEach { forecast ->
////            DailyForecastItem(forecast)
////        }
////    }
////}
////
////@Composable
////fun DailyForecastItem(forecast: DailyForecast) {
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = Modifier.padding(horizontal = 8.dp)
////    ) {
////        Text(
////            text = forecast.day,
////            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////        )
////
////        val iconRes = when (forecast.weatherType) {
////            "sunny" -> R.drawable.icon_pressure
////            "cloudy" -> R.drawable.icon_humidity
////            "rainy"  -> R.drawable.wind_speed_icon
////            else     -> R.drawable.icon_humidity
////        }
////
////        Icon(
////            painter = painterResource(id = iconRes),
////            contentDescription = "Forecast Icon",
////            tint = Color.White,
////            modifier = Modifier.size(24.dp)
////        )
////
////        Text(
////            text = "${forecast.temperature}°",
////            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
////        )
////    }
////}
////
////data class DailyForecast(
////    val day: String,
////    val temperature: Int,
////    val weatherType: String
////)
//
