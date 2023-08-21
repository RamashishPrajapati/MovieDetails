package com.ram.moviedetails.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieList")
data class Search(
    val Title: String,
    val Poster: String?,
    val Type: String,
    val Year: String,
    @PrimaryKey(autoGenerate = false) val imdbID: String
)