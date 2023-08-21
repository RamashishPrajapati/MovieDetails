package com.ram.moviedetails.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MovieRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)