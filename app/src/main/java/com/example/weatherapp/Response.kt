package com.example.weatherapp.utils

import com.example.weatherapp.data.models.CurrentWeatherForecast

sealed class Response {
    data object Loading : Response()
    data class Success(val data: CurrentWeatherForecast) : Response()
    data class Failure(val error: Throwable) : Response()
}
