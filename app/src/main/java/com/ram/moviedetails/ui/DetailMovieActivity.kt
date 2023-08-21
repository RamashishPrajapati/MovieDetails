package com.ram.moviedetails.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ram.moviedetails.R
import com.ram.moviedetails.databinding.ActivityDetailMovieBinding
import com.ram.moviedetails.utils.NetworkState
import com.ram.moviedetails.viewmodel.MovieDetailsViewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)
        initToolbar()
        initViewModel()

    }

    private fun initToolbar() {
        var toolbar = binding.toolbar
        var toolbarText = binding.toolbarTitle
        toolbarText.text = getString(R.string.movie_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun initViewModel() {
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        observeMovieDetails()
    }

    private fun observeMovieDetails() {
        val imdId = intent.getStringExtra(getString(R.string.imdid))
        imdId?.let { movieDetailsViewModel.getMovieDetails(it) }

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage(getString(R.string.please_wait_loading_the_details))

        movieDetailsViewModel.movieDetailsLiveData.observe(this, Observer { networkState ->
            networkState.let {
                when (networkState) {
                    is NetworkState.Error -> {
                        progressDialog.dismiss()
                        Toast.makeText(
                            this,
                            getString(R.string.something_went_wrong_try_again_later),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is NetworkState.Success -> {
                        progressDialog.dismiss()
                        binding.movieDetails = networkState.data
                    }

                    is NetworkState.Loading -> {
                        progressDialog.show()
                    }
                }
            }

        })
    }


}