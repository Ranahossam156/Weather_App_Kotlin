package com.example.weatherapp.data.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesDataSource private constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        @Volatile
        private var instance: SharedPreferencesDataSource? = null

        fun getInstance(context: Context): SharedPreferencesDataSource {
            return instance ?: synchronized(this) {
                instance ?: createInstance(context.applicationContext).also { instance = it }
            }
        }

        private fun createInstance(context: Context): SharedPreferencesDataSource {
            val prefs = context.getSharedPreferences(
                "weather_preferences",
                Context.MODE_PRIVATE
            )
            return SharedPreferencesDataSource(prefs)
        }
    }


    fun getTemperatureSetting(): String {
        return sharedPreferences.getString("temperature", "Celsius") ?: "Celsius"
    }

    fun getLanguageSetting(): String {
        return sharedPreferences.getString("language", "English") ?: "English"
    }

    fun getLocationSetting(): String {
        return sharedPreferences.getString("location", "GPS") ?: "GPS"
    }

    fun getWindSpeedSetting(): String {
        return sharedPreferences.getString("windSpeed", "Mile/Hour") ?: "Mile/Hour"
    }

    fun getNotificationSetting(): String {
        return sharedPreferences.getString("notification", "Enable") ?: "Enable"
    }

    fun setTemperatureSetting(value: String) {
        sharedPreferences.edit().putString("temperature", value).apply()
    }

    fun setLanguageSetting(value: String) {
        sharedPreferences.edit().putString("language", value).apply()
    }

    fun setLocationSetting(value: String) {
        sharedPreferences.edit().putString("location", value).apply()
    }

    fun setWindSpeedSetting(value: String) {
        sharedPreferences.edit().putString("windSpeed", value).apply()
    }

    fun setNotificationSetting(value: String) {
        sharedPreferences.edit().putString("notification", value).apply()
    }
}