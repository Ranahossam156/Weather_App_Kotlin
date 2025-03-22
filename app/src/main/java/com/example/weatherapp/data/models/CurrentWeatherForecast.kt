package com.example.weatherapp.data.models

data class CurrentWeatherForecast(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: MainX,
    val name: String,
    val rain: RainX,
    val sys: SysX,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)