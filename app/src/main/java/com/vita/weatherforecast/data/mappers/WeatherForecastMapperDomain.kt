package com.vita.weatherforecast.data.mappers

import com.vita.weatherforecast.data.remote.response.City
import com.vita.weatherforecast.data.remote.response.Clouds
import com.vita.weatherforecast.data.remote.response.Coord
import com.vita.weatherforecast.data.remote.response.Main
import com.vita.weatherforecast.data.remote.response.Rain
import com.vita.weatherforecast.data.remote.response.Sys
import com.vita.weatherforecast.data.remote.response.Weather
import com.vita.weatherforecast.data.remote.response.WeatherList
import com.vita.weatherforecast.data.remote.response.WeatherLocationResponse
import com.vita.weatherforecast.data.remote.response.Wind
import com.vita.weatherforecast.domain.model.CityDomain
import com.vita.weatherforecast.domain.model.CloudsDomain
import com.vita.weatherforecast.domain.model.CoordDomain
import com.vita.weatherforecast.domain.model.MainDomain
import com.vita.weatherforecast.domain.model.RainDomain
import com.vita.weatherforecast.domain.model.SysDomain
import com.vita.weatherforecast.domain.model.WeatherDomain
import com.vita.weatherforecast.domain.model.WeatherListDomain
import com.vita.weatherforecast.domain.model.WeatherLocationResponseDomain
import com.vita.weatherforecast.domain.model.WindDomain

class WeatherForecastMapperDomain {
    fun mapDataToDomain(weatherLocationResponse: WeatherLocationResponse?) : WeatherLocationResponseDomain? {
        if (weatherLocationResponse == null) return null
        return with(weatherLocationResponse) {
            WeatherLocationResponseDomain(
                city = city?.toCityDomain(),
                cnt = cnt,
                cod = cod,
                list = list?.map { it.toWeatherListDomain() },
                message = message
            )
        }
    }

    fun City.toCityDomain() : CityDomain {
        return CityDomain(
            coord = coord?.toCoordDomain(),
            country = country,
            id = id,
            name = name,
            population = population,
            sunrise = sunrise,
            sunset = sunset,
            timezone = timezone
        )
    }

    fun WeatherList.toWeatherListDomain() : WeatherListDomain {
        return WeatherListDomain(
            clouds = clouds?.toCloudsDomain(),
            dt = dt,
            dtTxt = dtTxt,
            main = main?.toMainDomain(),
            pop = pop,
            rain = rain?.toRainDomain(),
            sys = sys?.toSysDomain(),
            visibility = visibility,
            weather = weather?.map { it.toWeatherDomain() },
            wind = wind?.toWindDomain()
        )
    }

    fun Coord.toCoordDomain() : CoordDomain {
        return CoordDomain(
            lat = lat,
            lon = lon
        )
    }

    fun Clouds.toCloudsDomain() : CloudsDomain {
        return CloudsDomain(
            all = all
        )
    }

    fun Main.toMainDomain() : MainDomain {
        return MainDomain(
            feelsLike = feelsLike,
            grndLevel = grndLevel,
            humidity = humidity,
            pressure = pressure,
            seaLevel = seaLevel,
            temp = temp,
            tempKf = tempKf,
            tempMax = tempMax,
            tempMin = tempMin
        )
    }

    fun Rain.toRainDomain() : RainDomain {
        return RainDomain(
            jsonMember3h = jsonMember3h
        )
    }

    fun Sys.toSysDomain() : SysDomain {
        return SysDomain(
            pod = pod
        )
    }

    fun Weather.toWeatherDomain() : WeatherDomain {
        return WeatherDomain(
            description = description,
            icon = icon,
            id = id,
            main = main
        )
    }

    fun Wind.toWindDomain() : WindDomain {
        return WindDomain(
            deg = deg,
            gust = gust,
            speed = speed
        )
    }
}