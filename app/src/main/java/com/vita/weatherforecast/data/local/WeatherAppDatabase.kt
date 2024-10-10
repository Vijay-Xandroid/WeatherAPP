package com.vita.weatherforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vita.weatherforecast.data.local.entity.WeatherLocationEntity
import com.vita.weatherforecast.data.local.dao.CurrentWeatherDao
import com.vita.weatherforecast.data.local.dao.ForecastDao
import com.vita.weatherforecast.data.local.entity.WeatherListEntity

import com.vita.weatherforecast.utils.DataConverter

@Database(
    entities = [
        WeatherLocationEntity::class,
        WeatherListEntity::class
    ],
    version = 1
)

@TypeConverters(DataConverter::class)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract val currentWeatherDao: CurrentWeatherDao
    abstract val forecastDao: ForecastDao
}