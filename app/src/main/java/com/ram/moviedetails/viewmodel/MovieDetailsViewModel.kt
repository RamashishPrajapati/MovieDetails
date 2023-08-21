package com.ram.moviedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ram.moviedetails.model.MovieDetailsModel
import com.ram.moviedetails.repository.MovieDetailsRepository
import com.ram.moviedetails.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private var movieDetailsRepository: MovieDetailsRepository = MovieDetailsRepository()
    var movieDetailsLiveData = MutableLiveData<NetworkState<MovieDetailsModel>>()


    fun getMovieDetails(imdId: String) {
        movieDetailsLiveData.value = NetworkState.Loading()

        viewModelScope.launch(Dispatchers.IO) {
            val response = movieDetailsRepository.getMovieDetails(imdId)
            movieDetailsLiveData.postValue(response)
        }
    }


}