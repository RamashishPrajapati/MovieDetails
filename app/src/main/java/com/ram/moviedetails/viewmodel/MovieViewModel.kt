package com.ram.moviedetails.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ram.moviedetails.database.MovieDatabase
import com.ram.moviedetails.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private var movieRepository: MovieRepository =
        MovieRepository(MovieDatabase.getDatabase(application))

    val movieList = movieRepository.getQuotes().cachedIn(viewModelScope)
}