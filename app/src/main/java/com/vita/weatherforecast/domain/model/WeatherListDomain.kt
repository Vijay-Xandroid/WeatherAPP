package com.vita.weatherforecast.domain.model

import android.os.Parcelable

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherListDomain(
    val clouds: CloudsDomain?,
    val dt: Long?,
    @Json(name = "dt_txt")
    val dtTxt: String?,
    val main: MainDomain?,
    val pop: Double?,
    val rain: RainDomain?,
    val sys: SysDomain?,
    val visibility: Int?,
    val weather: List<WeatherDomain>?,
    val wind: WindDomain?
): Parcelable