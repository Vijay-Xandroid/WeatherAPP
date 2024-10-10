package com.vita.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.vita.weatherforecast.data.remote.response.City
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CityName")
data class CityEntity(
    @Embedded
    var cityCoord: CoordEntity?,
    @ColumnInfo(name = "cityCountry")
    var cityCountry: String?,
    @ColumnInfo(name = "cityId")
    var cityId: Int?,
    @ColumnInfo(name = "cityName")
    var cityName: String?,
    @ColumnInfo(name = "cityPopulation")
    var cityPopulation: Int?,
    @ColumnInfo(name = "citySunrise")
    var citySunrise: Int?,
    @ColumnInfo(name = "citySunset")
    var citySunset: Int?,
    @ColumnInfo(name = "cityTimezone")
    var cityTimezone: Int?
): Parcelable {
    @Ignore
    constructor(city: City) : this(
        cityId = city.id,
        cityCoord = city.coord?.let { CoordEntity(it) },
        cityName = city.name,
        cityCountry = city.country,
        cityPopulation = city.population,
        citySunrise = city.sunrise,
        citySunset = city.sunset,
        cityTimezone = city.timezone
    )

    fun getCityAndCountry(): String {
        return if (cityCountry.equals("none")) {
            "$cityName"
        } else {
            "$cityName, $cityCountry"
        }
    }
}