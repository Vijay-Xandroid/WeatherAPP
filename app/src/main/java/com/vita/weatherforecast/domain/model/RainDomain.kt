package com.vita.weatherforecast.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RainDomain(
    @Json(name = "`3h`")
    val jsonMember3h: Double?
): Parcelable