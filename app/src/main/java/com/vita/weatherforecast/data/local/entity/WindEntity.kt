package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.Wind
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Wind")
data class WindEntity(
    val deg: Int?,
    val gust: Double?,
    val speed: Double?
): Parcelable {
    @Ignore
    constructor(wind: Wind?) : this(
        deg = wind?.deg,
        gust = wind?.gust,
        speed = wind?.speed
    )
}