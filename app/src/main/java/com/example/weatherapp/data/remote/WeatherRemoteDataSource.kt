package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import java.io.IOException

class WeatherRemoteDataSource(private val service: WeatherService) : IWeatherRemoteDataSource {
    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Flow<CurrentWeatherForecast?> {
        return try {
            val response = service.getWeather(lat, lon, apiKey)
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                flowOf(null)
            }
        } catch (e: IOException) {
            throw Exception("Network Error: ${e.message}")
        } catch (e: HttpException) {
            throw Exception("HTTP Error: ${e.message}")
        }
    }

    override suspend fun getFutureForecast(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Flow<FiveDaysForecast?> {
        return try {
            val response = service.getFutureForecast(lat, lon, apiKey)
            if (response.isSuccessful) {
                flowOf(response.body())
            } else {
                flowOf(null)
            }
        } catch (e: IOException) {
            throw Exception("Network Error: ${e.message}")
        } catch (e: HttpException) {
            throw Exception("HTTP Error: ${e.message}")
        }
    }
}
