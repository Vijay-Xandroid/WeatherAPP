package com.vita.weatherforecast.data.repositoryImpl

import android.content.Context
import com.vita.weatherforecast.data.mappers.WeatherForecastMapperData
import com.vita.weatherforecast.data.local.WeatherAppDatabase
import com.vita.weatherforecast.data.mappers.WeatherForecastMapperDomain

import com.vita.weatherforecast.data.mappers.WeatherForecastEntityMapper

import com.vita.weatherforecast.data.remote.WeatherApiService
import com.vita.weatherforecast.domain.model.WeatherLocationResponseDomain
import com.vita.weatherforecast.domain.repository.WeatherForecastRepository
import com.vita.weatherforecast.utils.ApiResult
import com.vita.weatherforecast.utils.NetworkUtils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val weatherAppDatabase: WeatherAppDatabase
): WeatherForecastRepository {

    private val weatherForecastEntityMapper: WeatherForecastEntityMapper = WeatherForecastEntityMapper()
    private val weatherForecastMapperDomain : WeatherForecastMapperDomain = WeatherForecastMapperDomain()
    private val weatherForecastMapperData: WeatherForecastMapperData = WeatherForecastMapperData()

    override suspend fun getWeatherForecast(
        context: Context,
        latitude: Double,
        longitude: Double
    ): Flow<ApiResult<WeatherLocationResponseDomain>> {
        return flow {
            emit(ApiResult.Loading(true))

            val localWeatherForecast = weatherAppDatabase.forecastDao.getWeatherForecast()

            val shouldLoadLocalWeather = localWeatherForecast?.list?.isNotEmpty() == true && !NetworkUtils.isNetworkAvailable(context)

            if (shouldLoadLocalWeather) {
                emit(ApiResult.Success(
                    data = localWeatherForecast.let { weatherForecastEntity ->
                        weatherForecastEntityMapper.mapToWeatherLocationEntity(weatherForecastEntity)
                    }
                ))

                emit(ApiResult.Loading(false))
                return@flow
            }

            val getCurrentWeatherForecast = try {
                weatherApiService.getWeatherForecast(latitude, longitude)
            } catch (e: IOException) {
                emit(ApiResult.Error("Error loading weather"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading weather"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading weather"))
                return@flow
            }

            val weatherForecastEntities = getCurrentWeatherForecast.let { weatherForecastResponse ->
                weatherForecastMapperData.mapDataToEntity(weatherForecastResponse)
            }

            weatherForecastEntities?.let { weatherAppDatabase.forecastDao.upsertWeatherForecast(it) }

            emit(ApiResult.Success(
                weatherForecastEntities.let {
                    weatherForecastEntityMapper.mapToWeatherLocationEntity(it)
                }
            ))

//            emit(ApiResult.Success(
//                weatherForecastMapperDomain.mapDataToDomain(getCurrentWeatherForecast)
//            ))
            emit(ApiResult.Loading(false))
        }
    }

    override suspend fun getWeatherByCity(
        cityName: String
    ): Flow<ApiResult<WeatherLocationResponseDomain>> {
        return flow {
            emit(ApiResult.Loading(true))

            val getWeatherByCity = try {
                weatherApiService.getWeatherByCity(cityName)
            } catch (e: IOException) {
                emit(ApiResult.Error("Error loading weather"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading weather"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading weather"))
                return@flow
            }

            emit(ApiResult.Success(
                weatherForecastMapperDomain.mapDataToDomain(getWeatherByCity)
            ))
            emit(ApiResult.Loading(false))
        }
    }
}