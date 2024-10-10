package com.vita.weatherforecast.data.mappers

import com.vita.weatherforecast.data.local.entity.CityEntity
import com.vita.weatherforecast.data.local.entity.CoordEntity
import com.vita.weatherforecast.data.local.entity.WeatherLocationEntity
import com.vita.weatherforecast.data.remote.response.Clouds
import com.vita.weatherforecast.data.remote.response.Main
import com.vita.weatherforecast.data.remote.response.Rain
import com.vita.weatherforecast.data.remote.response.Sys
import com.vita.weatherforecast.data.remote.response.WeatherList
import com.vita.weatherforecast.domain.model.CityDomain
import com.vita.weatherforecast.domain.model.CloudsDomain
import com.vita.weatherforecast.domain.model.CoordDomain
import com.vita.weatherforecast.domain.model.MainDomain
import com.vita.weatherforecast.domain.model.RainDomain
import com.vita.weatherforecast.domain.model.WeatherDomain
import com.vita.weatherforecast.domain.model.WeatherListDomain
import com.vita.weatherforecast.domain.model.WeatherLocationResponseDomain
import com.vita.weatherforecast.domain.model.WindDomain
import com.vita.weatherforecast.data.remote.response.Weather

import com.vita.weatherforecast.data.remote.response.Wind
import com.vita.weatherforecast.domain.model.SysDomain


class WeatherForecastEntityMapper {

//    fun mapToWeatherLocationEntity(weatherList: WeatherList?) : WeatherListDomain? {
//        if (weatherList == null) return null
//        return with(weatherList) {
//            WeatherListDomain(
//                clouds = clouds?.toCloudsDomain(),
//                dt = dt,
//                dtTxt = dtTxt,
//                main = main?.toMainDomain(),
//                pop = pop,
//                rain = rain?.toRainDomain(),
//                sys = sys?.toSysDomain(),
//                visibility = visibility,
//                weather = weather?.map { it.toWeatherDomain() },
//                wind = wind?.toWindDomain()
//            )
//        }
//    }

    fun mapToWeatherLocationEntity(weatherLocationEntity: WeatherLocationEntity?) : WeatherLocationResponseDomain? {
        if (weatherLocationEntity == null) return null
        return with(weatherLocationEntity) {
            WeatherLocationResponseDomain(
                city = city?.toCityDomain(),
                cnt = cnt,
                cod = cod,
                list = list?.map { it.toWeatherListDomain() },
                message = message
            )
        }
    }

    fun CityEntity.toCityDomain() : CityDomain {
        return CityDomain(
            coord = cityCoord?.toCoordDomain(),
            country = cityCountry,
            id = cityId,
            name = cityName,
            population = cityPopulation,
            sunrise = citySunrise,
            sunset = citySunset,
            timezone = cityTimezone
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

    fun CoordEntity.toCoordDomain() : CoordDomain {
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