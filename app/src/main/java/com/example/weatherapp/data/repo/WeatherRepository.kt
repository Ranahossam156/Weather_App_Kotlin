
package com.example.weatherapp.data.repo

import com.example.weatherapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): Flow<Response>
}