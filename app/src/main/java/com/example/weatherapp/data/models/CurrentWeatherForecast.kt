package com.example.weatherapp.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "weather_table")
@TypeConverters(
    CoordConverter::class, MainXConverter::class, WindConverter::class,
    CloudsConverter::class, SysXConverter::class, RainConverter::class, WeatherListConverter::class
)
data class CurrentWeatherForecast(
    @PrimaryKey val id: Int,
    val name: String,
    val base: String,
    val cod: Int,
    val dt: Int,
    val timezone: Int,
    val visibility: Int,

    val coord: Coord,
    val main: MainX,
    val wind: Wind,
    val clouds: Clouds,
    val sys: SysX,
    val rain: RainX?,
    val weather: List<Weather>?
)
class CoordConverter {
    @TypeConverter
    fun fromCoord(coord: Coord): String {
        return Gson().toJson(coord)
    }

    @TypeConverter
    fun toCoord(coordString: String): Coord {
        return Gson().fromJson(coordString, Coord::class.java)
    }
}
class MainXConverter {
    @TypeConverter
    fun fromMainX(mainX: MainX): String {
        return Gson().toJson(mainX)
    }

    @TypeConverter
    fun toMainX(mainXString: String): MainX {
        return Gson().fromJson(mainXString, MainX::class.java)
    }
}
class WindConverter {
    @TypeConverter
    fun fromWind(wind: Wind): String {
        return Gson().toJson(wind)
    }

    @TypeConverter
    fun toWind(windString: String): Wind {
        return Gson().fromJson(windString, Wind::class.java)
    }
}
class CloudsConverter {
    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        return Gson().toJson(clouds)
    }

    @TypeConverter
    fun toClouds(cloudsString: String): Clouds {
        return Gson().fromJson(cloudsString, Clouds::class.java)
    }
}
class SysXConverter {
    @TypeConverter
    fun fromSysX(sysX: SysX): String {
        return Gson().toJson(sysX)
    }

    @TypeConverter
    fun toSysX(sysXString: String): SysX {
        return Gson().fromJson(sysXString, SysX::class.java)
    }
}
class RainConverter {
    @TypeConverter
    fun fromRain(rain: RainX?): String {
        return Gson().toJson(rain)
    }

    @TypeConverter
    fun toRain(rainString: String): RainX? {
        return Gson().fromJson(rainString, RainX::class.java)
    }
}
class WeatherListConverter {
    @TypeConverter
    fun fromWeatherList(weather: List<Weather>?): String {
        return Gson().toJson(weather)
    }

    @TypeConverter
    fun toWeatherList(weatherString: String): List<Weather>? {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(weatherString, listType)
    }
}