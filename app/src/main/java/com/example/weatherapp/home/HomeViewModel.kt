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
import com.example.weatherapp.data.models.CurrentWeatherForecast
import com.example.weatherapp.data.models.FiveDaysForecast
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.data.repository.WeatherRepositoryImplementation
import com.example.weatherapp.utils.Response
import kotlinx.coroutines.flow.asStateFlow


class HomeViewModel(private val context: Context,private val repository: WeatherRepository) : ViewModel() {

    private val _currentCity = MutableStateFlow("Fetching location...")
    val currentCity: StateFlow<String> = _currentCity
    private val _navigateToSettings = MutableStateFlow(false)
    val navigateToSettings: StateFlow<Boolean> = _navigateToSettings
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val _futureWeatherState = MutableStateFlow<Response<FiveDaysForecast>>(Response.Loading)
    val futureWeatherState: StateFlow<Response<FiveDaysForecast>> = _futureWeatherState.asStateFlow()


    private val _weatherState = MutableStateFlow<Response<CurrentWeatherForecast>>(Response.Loading)
    val weatherState: StateFlow<Response<CurrentWeatherForecast>> = _weatherState.asStateFlow()

    fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkLocationEnabled()
        }
    }

    fun checkLocationEnabled() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isGpsEnabled || isNetworkEnabled) {
            getCurrentLocation()
        } else {
            viewModelScope.launch {
                _navigateToSettings.emit(true)
            }
        }
    }

    fun resetNavigationState() {
        viewModelScope.launch {
            _navigateToSettings.emit(false)
        }
    }
//    @SuppressLint("MissingPermission")
//    private fun getCurrentLocation() {
//        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    locationResult.lastLocation?.let { location ->
//                        val lat = location.latitude
//                        val lon = location.longitude
//                        viewModelScope.launch {
//                            _currentCity.emit(getCityName(lat, lon))
//                        }
//                        fetchWeather(lat, lon)
//                    }
//                }
//            },
//            null
//        )
//    }
@SuppressLint("MissingPermission")
private fun getCurrentLocation() {
    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener { location ->
            location?.let {
                val lat = it.latitude
                val lon = it.longitude
                viewModelScope.launch {
                    _currentCity.emit(getCityName(lat, lon))
                }
                fetchWeather(lat, lon)
                fetchFutureWeather(lat, lon)

            }
        }
        .addOnFailureListener { e ->
        }
}

    private fun getCityName(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses?.get(0)?.locality ?: "Unknown location"
    }
    fun fetchWeather(lat: Double, lon: Double) {
        val apiKey = "8312b9a01f34b592da08b4a0f069890f"
        viewModelScope.launch {
            repository.getCurrentWeather(lat, lon, apiKey).collect { forecast ->
                _weatherState.value = if (forecast != null) {
                    Response.Success(forecast)
                } else {
                    Response.Failure(Exception("No weather data found"))
                }
            }
        }
    }

    fun fetchFutureWeather(lat: Double, lon: Double) {
        val apiKey = "8312b9a01f34b592da08b4a0f069890f"
        viewModelScope.launch {
            repository.getFutureForecast(lat, lon, apiKey).collect { forecast ->
                _futureWeatherState.value = if (forecast != null) {
                    Response.Success(forecast)
                } else {
                    Response.Failure(Exception("No weather data found"))
                }
            }
        }
    }


}

class HomeViewModelFactory(private val context: Context, private val repository: WeatherRepositoryImplementation)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(context, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
