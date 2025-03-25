package com.example.weatherapp.data.repository

//import com.example.weatherapp.data.local.WeatherLocalDataSource
import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.utils.Response
import kotlinx.coroutines.flow.*

class WeatherRepositoryImplementation private constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    //private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): Flow<Response> = flow {
        emit(Response.Loading)

        try {
            remoteDataSource.getWeather(lat, lon, apiKey).collect { response ->
                if (response != null) {
                    emit(Response.Success(response))
                   // localDataSource.saveWeather(response)
                } else {
                    emit(Response.Failure(Exception("No weather data found")))
                }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    companion object {
        @Volatile
        private var instance: WeatherRepositoryImplementation? = null

        fun getInstance(remoteDataSource: WeatherRemoteDataSource): WeatherRepositoryImplementation {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImplementation(remoteDataSource).also { instance = it }
            }
        }
    }
}
