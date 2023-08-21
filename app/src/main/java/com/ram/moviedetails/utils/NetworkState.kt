package com.ram.moviedetails.utils

import retrofit2.Response

sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error<T>(val response: Response<T>) : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
}

