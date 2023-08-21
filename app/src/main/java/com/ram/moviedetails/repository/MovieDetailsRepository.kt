package com.ram.moviedetails.repository

import com.ram.moviedetails.model.MovieDetailsModel
import com.ram.moviedetails.retrofit.RetrofitInstance
import com.ram.moviedetails.utils.NetworkState

class MovieDetailsRepository {

    private val retrofit = RetrofitInstance.movieApi

    suspend fun getMovieDetails(imdId: String): NetworkState<MovieDetailsModel> {
        val response = retrofit.getMovieDetails(imdId)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}