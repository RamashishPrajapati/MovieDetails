package com.ram.moviedetails.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ram.moviedetails.R
import com.ram.moviedetails.adapter.MovieListAdapter
import com.ram.moviedetails.adapter.OnMovieClick
import com.ram.moviedetails.databinding.ActivityMovieListBinding
import com.ram.moviedetails.viewmodel.MovieViewModel

class MovieListActivity : AppCompatActivity(), OnMovieClick {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var movieViewModel: MovieViewModel
    private var movieListAdapter = MovieListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)

        initToolbar()
        initViewModel()

        binding.rvMovieList.apply {
            setRecyclerLayout()
            adapter = movieListAdapter
        }

    }

    private fun initToolbar() {
        var toolbar = binding.ilToolbar.findViewById<Toolbar>(R.id.toolbar)
        var toolbarText = binding.ilToolbar.findViewById<TextView>(R.id.toolbar_title)
        toolbarText.text = getString(R.string.app_name)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initViewModel() {
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        observerMovieData()


    }

    private fun observerMovieData() {
        movieViewModel.movieList.observe(this, Observer {
            movieListAdapter.submitData(lifecycle, it)
        })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setRecyclerLayout()
    }


    private fun setRecyclerLayout() {
        if (resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT) {
            binding.rvMovieList.layoutManager = GridLayoutManager(this, 3)
        } else {
            binding.rvMovieList.layoutManager = GridLayoutManager(this, 5)
        }
    }

    override fun onClick(imdId: String) {
        Intent(this, DetailMovieActivity::class.java).apply {
            putExtra(getString(R.string.imdid), imdId)
            startActivity(this)
        }
    }

}