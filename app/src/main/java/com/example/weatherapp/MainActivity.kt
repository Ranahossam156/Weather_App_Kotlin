package com.example.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.home.HomeViewModel
import com.example.weatherapp.home.HomeViewModelFactory
import com.example.weatherapp.home.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.*
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(this)).get(HomeViewModel::class.java)

        setContent {
            WeatherAppTheme {
                val city by viewModel.currentCity.collectAsState()
                val navigateToSettings by viewModel.navigateToSettings.collectAsState()

                LaunchedEffect(navigateToSettings) {
                    if (navigateToSettings) {
                        showLocationDisabledDialog()
                        viewModel.resetNavigationState()
                    }
                }

                WeatherScreen(city = city)
            }
        }
    }
    private fun showLocationDisabledDialog() {
        AlertDialog.Builder(this)
            .setTitle("Enable Location")
            .setMessage("Location services are required. Please enable them in settings.")
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onStart() {
        super.onStart()
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            viewModel.checkLocationEnabled()
        }
    }

    private fun requestLocationPermission() {
        requestPermissionsLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                viewModel.checkLocationEnabled()
            }
        }
}
