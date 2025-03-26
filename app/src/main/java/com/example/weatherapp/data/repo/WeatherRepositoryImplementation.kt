package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImplementation private constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): Flow<CurrentWeatherForecast?> {
        return remoteDataSource.getWeather(lat, lon, apiKey)
    }
    override suspend fun getFutureForecast(lat: Double, lon: Double, apiKey: String): Flow<FiveDaysForecast?> {
        return remoteDataSource.getFutureForecast(lat, lon, apiKey)
    }

    companion object {
        @Volatile
        private var instance: WeatherRepositoryImplementation? = null

        fun getInstance(remoteDataSource: WeatherRemoteDataSource): WeatherRepositoryImplementation {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImplementation(remoteDataSource).also { instance = it }
            }
        }
    }
}
