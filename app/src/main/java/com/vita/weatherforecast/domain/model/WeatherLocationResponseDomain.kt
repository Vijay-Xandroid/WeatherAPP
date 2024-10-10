package com.vita.weatherforecast.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherLocationResponseDomain(
    val city: CityDomain?,
    val cnt: Int?,
    val cod: String?,
    val list: List<WeatherListDomain>? = listOf(),
    val message: Int?
): Parcelable