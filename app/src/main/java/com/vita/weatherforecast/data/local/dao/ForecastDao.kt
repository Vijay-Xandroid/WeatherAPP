package com.vita.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vita.weatherforecast.data.local.entity.WeatherLocationEntity

@Dao
interface ForecastDao {
    @Upsert
    suspend fun upsertWeatherForecast(weatherLocationEntity: WeatherLocationEntity)

    @Query("SELECT * FROM Forecast")
    suspend fun getWeatherForecast(): WeatherLocationEntity?

    @Query("SELECT * FROM Forecast ORDER BY abs(:lat) AND abs(:lon) LIMIT 1")
    fun getForecastByCoord(lat: Double, lon: Double): LiveData<WeatherLocationEntity>

    @Delete
    fun deleteForecast(forecast: WeatherLocationEntity)

    @Query("DELETE FROM Forecast")
    fun deleteAll()

    @Query("Select count(*) from Forecast")
    fun getCount(): Int
}