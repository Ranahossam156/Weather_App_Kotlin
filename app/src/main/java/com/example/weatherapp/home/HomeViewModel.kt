package com.example.weatherapp.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.data.repository.WeatherRepositoryImplementation
import com.example.weatherapp.data.sharedPreferences.SharedPreferencesDataSource
import com.example.weatherapp.utils.Response
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel(
    private val repository: WeatherRepository,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : ViewModel() {

    private val _currentCity = MutableStateFlow("Fetching location...")
    val currentCity: StateFlow<String> = _currentCity.asStateFlow()

    private val _weatherState = MutableStateFlow<Response<CurrentWeatherForecast>>(Response.Loading)
    val weatherState: StateFlow<Response<CurrentWeatherForecast>> = _weatherState.asStateFlow()

    private val _futureWeatherState = MutableStateFlow<Response<FiveDaysForecast>>(Response.Loading)
    val futureWeatherState: StateFlow<Response<FiveDaysForecast>> = _futureWeatherState.asStateFlow()

    private var lastLat: Double? = null
    private var lastLon: Double? = null

    fun fetchWeather(lat: Double, lon: Double) {
        lastLat = lat
        lastLon = lon
        val apiKey = "8312b9a01f34b592da08b4a0f069890f"
        val units = (repository as? WeatherRepositoryImplementation)?.getUnits() ?: "metric"
        val lang = when (sharedPreferencesDataSource.getLanguageSetting().toLowerCase()) {
            "ar" -> "ar"
            else -> "en"
        }
        viewModelScope.launch {
            repository.getCurrentWeather(lat, lon, apiKey, units, lang).collect { forecast ->
                if (forecast != null) {
                    _weatherState.value = Response.Success(forecast)
                } else {
                    _weatherState.value = Response.Failure(Exception("No weather data found"))
                }
            }
        }
    }

    fun fetchFutureWeather(lat: Double, lon: Double) {
        lastLat = lat
        lastLon = lon
        val apiKey = "8312b9a01f34b592da08b4a0f069890f"
        val units = (repository as? WeatherRepositoryImplementation)?.getUnits() ?: "metric"
        val lang = when (sharedPreferencesDataSource.getLanguageSetting().toLowerCase()) {
            "arabic" -> "ar"
            else -> "en"
        }

        viewModelScope.launch {
            repository.getFutureForecast(lat, lon, apiKey, units, lang).collect { forecast ->
                if (forecast != null) {
                    _futureWeatherState.value = Response.Success(forecast)
                } else {
                    _futureWeatherState.value = Response.Failure(Exception("No weather data found"))
                }
            }
        }
    }

    fun refreshWeather() {
        lastLat?.let { lat ->
            lastLon?.let { lon ->
                fetchWeather(lat, lon)
                fetchFutureWeather(lat, lon)
            }
        }
        Log.d("HomeViewModel", "Refreshing weather for lat: $lastLat, lon: $lastLon")

    }

    fun updateCity(cityName: String) {
        viewModelScope.launch {
            _currentCity.emit(cityName)
        }
    }
}

class HomeViewModelFactory(
    private val repository: WeatherRepositoryImplementation,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository, sharedPreferencesDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
