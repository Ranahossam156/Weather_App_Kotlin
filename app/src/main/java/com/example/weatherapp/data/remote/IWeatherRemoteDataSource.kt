package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.CurrentWeatherForecast
import kotlinx.coroutines.flow.Flow

interface IWeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Flow<CurrentWeatherForecast?>
}