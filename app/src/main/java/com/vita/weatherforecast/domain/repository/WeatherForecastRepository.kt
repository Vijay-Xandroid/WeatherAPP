package com.vita.weatherforecast.domain.repository

import android.content.Context
import com.vita.weatherforecast.domain.model.WeatherLocationResponseDomain
import com.vita.weatherforecast.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface WeatherForecastRepository {
    suspend fun getWeatherForecast(
        context: Context,
        latitude: Double,
        longitude: Double
    ): Flow<ApiResult<WeatherLocationResponseDomain>>

    suspend fun getWeatherByCity(
        cityName: String
    ): Flow<ApiResult<WeatherLocationResponseDomain>>
}