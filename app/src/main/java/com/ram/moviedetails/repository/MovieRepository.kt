package com.ram.moviedetails.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ram.moviedetails.database.MovieDatabase
import com.ram.moviedetails.paging.MovieRemoteMediator
import com.ram.moviedetails.retrofit.RetrofitInstance

class MovieRepository(private val movieDatabase: MovieDatabase) {

    private val retrofit = RetrofitInstance.movieApi

    @OptIn(ExperimentalPagingApi::class)
    fun getQuotes() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100
        ),
        remoteMediator = MovieRemoteMediator(retrofit, movieDatabase),
        pagingSourceFactory = { movieDatabase.movieDao().getMovie() }
    ).liveData

}