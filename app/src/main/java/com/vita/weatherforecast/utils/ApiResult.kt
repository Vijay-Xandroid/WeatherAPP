package com.vita.weatherforecast.utils

sealed class ApiResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(message: String, data: T? = null) : ApiResult<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : ApiResult<T>(null)
}