package com.vita.weatherforecast.data.remote.response

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Parcelize
@JsonClass(generateAdapter = true)
data class WeatherList(
    @Json(name = "clouds")
    val clouds: Clouds?,
    val dt: Long?,
    @Json(name = "dt_txt")
    val dtTxt: String?,
    @Json(name = "main")
    val main: Main?,
    val pop: Double?,
    @Json(name = "rain")
    val rain: Rain?,
    @Json(name = "sys")
    val sys: Sys?,
    val visibility: Int?,
    @Json(name = "weather")
    val weather: List<Weather>?,
    @Json(name = "wind")
    val wind: Wind?
): Parcelable {
    fun getWeatherItem(): Weather? {
        return weather?.first()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDay(): String? {
        return dt?.let { getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
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

    fun getHourColor(): Int {
        return when (dtTxt?.substringAfter(" ")?.substringBeforeLast(":")) {
            "00:00" -> Color.parseColor("#28E0AE")
            "03:00" -> Color.parseColor("#FF0090")
            "06:00" -> Color.parseColor("#FFAE00")
            "09:00" -> Color.parseColor("#0090FF")
            "12:00" -> Color.parseColor("#DC0000")
            "15:00" -> Color.parseColor("#0051FF")
            "18:00" -> Color.parseColor("#3D28E0")
            "21:00" -> Color.parseColor("#50E3FE")
            else -> Color.parseColor("#28E0AE")
        }
    }

    fun getHourOfDay(): String {
        return dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
    }
}