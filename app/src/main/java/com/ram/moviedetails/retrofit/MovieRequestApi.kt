package com.ram.moviedetails.retrofit

import com.ram.moviedetails.model.MovieDetailsModel
import com.ram.moviedetails.model.MovieListModel
import com.ram.moviedetails.utils.Constants
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieRequestApi {
    @POST(Constants.MOVIE_LIST)
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieListModel>


    @POST(Constants.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Query("i") page: String): Response<MovieDetailsModel>


}