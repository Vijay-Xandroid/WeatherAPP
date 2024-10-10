package com.vita.weatherforecast.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vita.weatherforecast.domain.model.WeatherLocationResponseDomain
import com.vita.weatherforecast.domain.repository.WeatherForecastRepository
import com.vita.weatherforecast.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewmodel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
): ViewModel() {
    private var _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow: Flow<Boolean> = _isLoadingFlow

    private val _weatherData = MutableStateFlow<WeatherLocationResponseDomain?>(null)
    val weatherData = _weatherData.asStateFlow()

    fun fetchWeather(
        context: Context,
        latitude: Double,
        longtitude: Double
    ) {
        viewModelScope.launch {
            _isLoadingFlow.tryEmit(true)

            weatherForecastRepository.getWeatherForecast(
                context, latitude, longtitude
            ).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        _isLoadingFlow.tryEmit(false)
                    }
                    is ApiResult.Loading -> {
                        result.isLoading
                    }
                    is ApiResult.Success -> {
                        _isLoadingFlow.tryEmit(false)
                        result.data.let {
                           _weatherData.value = it
                        }
                    }
                }
            }
        }
    }

    fun fetchWeatherByCity(
        cityName: String
    ) {
        viewModelScope.launch {
            _isLoadingFlow.tryEmit(true)

            weatherForecastRepository.getWeatherByCity(cityName).collectLatest { result ->
                when (result) {
                    is ApiResult.Error -> {
                        _isLoadingFlow.tryEmit(false)
                    }
                    is ApiResult.Loading -> {
                        result.isLoading
                    }
                    is ApiResult.Success -> {
                        _isLoadingFlow.tryEmit(false)
                        result.data.let {
                            _weatherData.value = it
                        }
                    }
                }
            }
        }
    }
}