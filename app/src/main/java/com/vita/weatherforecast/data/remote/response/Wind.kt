package com.vita.weatherforecast.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Wind(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
): Parcelable