package com.vita.weatherforecast.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lat: Double?,
    val lon: Double?
)