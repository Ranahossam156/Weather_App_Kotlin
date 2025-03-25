//package com.example.weatherapp.data.local
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.example.weatherapp.data.models.CloudsConverter
//import com.example.weatherapp.data.models.CoordConverter
//import com.example.weatherapp.data.models.CurrentWeatherForecast
//import com.example.weatherapp.data.models.MainXConverter
//import com.example.weatherapp.data.models.RainConverter
//import com.example.weatherapp.data.models.SysXConverter
//import com.example.weatherapp.data.models.WeatherListConverter
//import com.example.weatherapp.data.models.WindConverter
//import com.example.weatherapp.utils.*
//
//@Database(entities = [CurrentWeatherForecast::class], version = 1)
//@TypeConverters(
//    CoordConverter::class, MainXConverter::class, WindConverter::class,
//    CloudsConverter::class, SysXConverter::class, RainConverter::class, WeatherListConverter::class
//)
//abstract class WeatherDatabase : RoomDatabase() {
//    abstract fun weatherDao(): WeatherDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: WeatherDatabase? = null
//
//        fun getDatabase(context: Context): WeatherDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WeatherDatabase::class.java,
//                    "weather_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
