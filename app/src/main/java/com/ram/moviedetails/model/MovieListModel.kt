package com.ram.moviedetails.model

data class MovieListModel(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)