package com.ram.moviedetails.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ram.moviedetails.R
import com.ram.moviedetails.databinding.ActivitySplashScreenBinding

import com.ram.moviedetails.viewmodel.SplashViewModel

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        initViewModel()
        observerSplashData()
    }

    private fun initViewModel() {
        splashViewModel = ViewModelProvider(this)[SplashViewModel::class.java]

    }

    private fun observerSplashData() {
        splashViewModel.initSplashScreen()
        splashViewModel.splashLiveData.observe(this, Observer {
            finish()
            startActivity(Intent(this, MovieListActivity::class.java))
        })
    }
}