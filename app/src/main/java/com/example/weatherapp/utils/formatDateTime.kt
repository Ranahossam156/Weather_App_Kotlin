package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateTime(unixTime: Int): String {
    val date = Date(unixTime.toLong() * 1000)
    val format = SimpleDateFormat("EEE, MMM d, hh:mm a", Locale.getDefault())
    return format.format(date)
}
