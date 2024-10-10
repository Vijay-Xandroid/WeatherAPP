package com.vita.weatherforecast.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CoordDomain(
    val lat: Double?,
    val lon: Double?
): Parcelable