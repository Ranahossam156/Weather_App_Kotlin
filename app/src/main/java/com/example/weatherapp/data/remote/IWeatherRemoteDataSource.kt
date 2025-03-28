package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import kotlinx.coroutines.flow.Flow

interface IWeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String, units: String, lang: String): Flow<CurrentWeatherForecast?>
    suspend fun getFutureForecast(lat: Double, lon: Double, apiKey: String, units: String, lang: String): Flow<FiveDaysForecast?>

}