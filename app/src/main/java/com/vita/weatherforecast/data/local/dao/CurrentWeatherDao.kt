package com.vita.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vita.weatherforecast.data.local.entity.WeatherListEntity

@Dao
interface CurrentWeatherDao {
    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather(): LiveData<WeatherListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(weatherListEntity: WeatherListEntity)

    @Query("DELETE FROM CurrentWeather")
    fun deleteCurrentWeather()

    @Query("Select count(*) from CurrentWeather")
    fun getCount(): Int
}