package com.example.weatherapp.utils


sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
    data class Failure(val error: Throwable) : Response<Nothing>()
}

