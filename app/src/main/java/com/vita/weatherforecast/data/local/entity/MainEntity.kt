package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.Main
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Main")
data class MainEntity(
    @ColumnInfo(name = "feelsLike")
    val feelsLike: Double?,
    @ColumnInfo(name = "grndLevel")
    val grndLevel: Int?,
    @ColumnInfo(name = "humidity")
    val humidity: Int?,
    @ColumnInfo(name = "pressure")
    val pressure: Int?,
    @ColumnInfo(name = "seaLevel")
    val seaLevel: Int?,
    @ColumnInfo(name = "temp")
    val temp: Double?,
    @ColumnInfo(name = "tempKf")
    val tempKf: Double?,
    @ColumnInfo(name = "tempMax")
    val tempMax: Double?,
    @ColumnInfo(name = "tempMin")
    val tempMin: Double?
): Parcelable {
    @Ignore
    constructor(main: Main?) : this(
        feelsLike = main?.feelsLike,
        grndLevel = main?.grndLevel,
        humidity = main?.humidity,
        pressure = main?.pressure,
        seaLevel = main?.seaLevel,
        temp = main?.temp,
        tempKf = main?.tempKf,
        tempMin = main?.tempMin,
        tempMax = main?.tempMax
    )

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return humidity.toString() + "°"
    }
}