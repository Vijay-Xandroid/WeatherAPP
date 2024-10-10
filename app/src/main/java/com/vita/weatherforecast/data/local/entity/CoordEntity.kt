package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.Coord
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Coord")
data class CoordEntity(
    @ColumnInfo(name = "lat")
    val lat: Double?,
    @ColumnInfo(name = "lon")
    val lon: Double?
): Parcelable {
    @Ignore
    constructor(coord: Coord) : this(
        lon = coord.lon,
        lat = coord.lat
    )
}