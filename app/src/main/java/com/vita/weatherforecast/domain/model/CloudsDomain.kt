package com.vita.weatherforecast.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CloudsDomain(
    val all: Int?
): Parcelable