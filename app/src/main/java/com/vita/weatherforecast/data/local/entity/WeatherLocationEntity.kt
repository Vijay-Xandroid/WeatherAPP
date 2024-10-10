package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.vita.weatherforecast.data.remote.response.WeatherList
import com.vita.weatherforecast.data.remote.response.WeatherLocationResponse
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Forecast")
data class WeatherLocationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @Embedded
    var city: CityEntity?,
    @ColumnInfo(name = "cnt")
    val cnt: Int?,
    @ColumnInfo(name = "cod")
    val cod: String?,
    @ColumnInfo(name = "list")
    val list: List<WeatherList>?,
    @ColumnInfo(name = "message")
    val message: Int?
): Parcelable {
    @Ignore
    constructor(weatherLocationResponse: WeatherLocationResponse?) : this(
        id = 0,
        city = weatherLocationResponse?.city?.let { CityEntity(it) },
        cnt = weatherLocationResponse?.cnt,
        cod = weatherLocationResponse?.cod,
        list = weatherLocationResponse?.list,
        message = weatherLocationResponse?.message
    )
}