package com.vita.weatherforecast.utils

import androidx.room.TypeConverter
import com.vita.weatherforecast.data.remote.response.Weather
import com.vita.weatherforecast.data.remote.response.WeatherList
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object DataConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<WeatherList>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, WeatherList::class.java)
        val adapter = moshi.adapter<List<WeatherList>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<WeatherList>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, WeatherList::class.java)
        val adapter = moshi.adapter<List<WeatherList>>(type)
        return adapter.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun weatherStringToList(data: String?): List<Weather>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Weather::class.java)
        val adapter = moshi.adapter<List<Weather>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun weatherListToString(objects: List<Weather>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Weather::class.java)
        val adapter = moshi.adapter<List<Weather>>(type)
        return adapter.toJson(objects)
    }
}