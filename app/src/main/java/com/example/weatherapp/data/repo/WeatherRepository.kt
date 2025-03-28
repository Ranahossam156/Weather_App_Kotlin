
package com.example.weatherapp.data.repo

import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import com.example.weatherapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String, units: String, lang: String): Flow<CurrentWeatherForecast?>
    suspend fun getFutureForecast(lat: Double, lon: Double, apiKey: String, units: String, lang: String): Flow<FiveDaysForecast?>
}