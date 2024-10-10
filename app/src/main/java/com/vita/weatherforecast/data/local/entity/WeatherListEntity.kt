package com.vita.weatherforecast.data.local.entity

import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.vita.weatherforecast.data.remote.response.Weather
import com.vita.weatherforecast.data.remote.response.WeatherList
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Parcelize
@Entity(tableName = "CurrentWeather")
data class WeatherListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @Embedded
    val clouds: CloudsEntity?,
    @ColumnInfo(name = "dt")
    val dt: Long?,
    val dtTxt: String?,
    @Embedded
    val main: MainEntity,
    val pop: Double?,
    @Embedded
    val rain: RainEntity,
    @Embedded
    val sys: SysEntity,
    @ColumnInfo(name = "visibility")
    val visibility: Int?,
    @ColumnInfo(name = "weather")
    val weather: List<Weather?>? = null,
    @Embedded
    val wind: WindEntity?
): Parcelable {
    @Ignore
    constructor(weatherList: WeatherList) : this(
        id = 0,
        clouds = CloudsEntity(weatherList.clouds),
        dt = weatherList.dt?.toLong(),
        dtTxt = weatherList.dtTxt,
        main = MainEntity(weatherList.main),
        pop = weatherList.pop?.toDouble(),
        rain = RainEntity(weatherList.rain),
        sys = SysEntity(weatherList.sys),
        visibility = weatherList.visibility,
        weather = weatherList.weather,
        wind = WindEntity(weatherList.wind)
    )

    fun getCurrentWeather(): Weather? {
        return weather?.first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getColor(): Int {
        return when (dt?.let { getDateTime(it) }) {
            DayOfWeek.MONDAY -> Color.parseColor("#28E0AE")
            DayOfWeek.TUESDAY -> Color.parseColor("#FF0090")
            DayOfWeek.WEDNESDAY -> Color.parseColor("#FFAE00")
            DayOfWeek.THURSDAY -> Color.parseColor("#0090FF")
            DayOfWeek.FRIDAY -> Color.parseColor("#DC0000")
            DayOfWeek.SATURDAY -> Color.parseColor("#0051FF")
            DayOfWeek.SUNDAY -> Color.parseColor("#3D28E0")
            else -> Color.parseColor("#28E0AE")
        }
    }
}