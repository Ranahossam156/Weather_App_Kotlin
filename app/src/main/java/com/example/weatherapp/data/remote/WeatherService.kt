package com.example.weatherapp.data.remote

import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Response<CurrentWeatherForecast>

    @GET("forecast")
    suspend fun getFutureForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Response<FiveDaysForecast>
}

