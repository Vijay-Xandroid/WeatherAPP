package com.vita.weatherforecast.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vita.weatherforecast.data.remote.response.City
import com.vita.weatherforecast.data.remote.response.WeatherList

@JsonClass(generateAdapter = true)
data class WeatherLocationResponse(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    @Json(name = "list")
    val list: List<WeatherList>? = listOf(),
    val message: Int?
)