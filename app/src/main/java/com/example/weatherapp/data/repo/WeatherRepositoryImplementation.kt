package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import com.example.weatherapp.data.sharedPreferences.SharedPreferencesDataSource
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImplementation private constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : WeatherRepository {

    private fun mapTemperatureUnit(tempSetting: String): String {
        return when (tempSetting) {
            "Kelvin" -> "standard"
            "Celsius" -> "metric"
            "Fahrenheit" -> "imperial"
            else -> "metric"
        }
    }

    private fun mapLanguage(languageSetting: String): String {
        return if (languageSetting == "Arabic") "ar" else "en"
    }

    fun getUnits(): String {
        val tempSetting = sharedPreferencesDataSource.getTemperatureSetting()
        return mapTemperatureUnit(tempSetting)
    }

    fun getLang(): String {
        val languageSetting = sharedPreferencesDataSource.getLanguageSetting()
        return mapLanguage(languageSetting)
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String,
        lang: String
    ): Flow<CurrentWeatherForecast?> {
        return remoteDataSource.getWeather(lat, lon, apiKey, units, lang)
    }

    override suspend fun getFutureForecast(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String,
        lang: String
    ): Flow<FiveDaysForecast?> {
        return remoteDataSource.getFutureForecast(lat, lon, apiKey, units, lang)
    }

    companion object {
        @Volatile
        private var instance: WeatherRepositoryImplementation? = null

        fun getInstance(
            remoteDataSource: WeatherRemoteDataSource,
            sharedPreferencesDataSource: SharedPreferencesDataSource
        ): WeatherRepositoryImplementation {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImplementation(remoteDataSource, sharedPreferencesDataSource)
                    .also { instance = it }
            }
        }

    }
}
