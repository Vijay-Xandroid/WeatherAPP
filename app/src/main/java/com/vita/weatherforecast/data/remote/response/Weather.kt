package com.vita.weatherforecast.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    val description: String?,
    val icon: String?,
    val id: Int?,
    val main: String?
): Parcelable {
    fun getDescriptionText(): String? {
        return description?.capitalize()
    }
}