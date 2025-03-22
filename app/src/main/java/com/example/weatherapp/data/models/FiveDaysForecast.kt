package com.example.weatherapp.data.models

data class FiveDaysForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item8>,
    val message: Int
)