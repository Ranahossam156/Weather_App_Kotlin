package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.CurrentWeatherForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WeatherRemoteDataSource(private val service: WeatherService) : IWeatherRemoteDataSource {
    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Flow<CurrentWeatherForecast?> = flow {
        try {
            val response = service.getWeather(lat, lon, apiKey)
            if (response.isSuccessful) {
                emit(response.body())
            } else {
                throw HttpException(response)
            }
        } catch (e: IOException) {
            throw Exception("Network Error: ${e.message}")
        }
    }
}
