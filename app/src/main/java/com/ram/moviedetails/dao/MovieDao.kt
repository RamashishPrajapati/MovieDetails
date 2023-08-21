package com.ram.moviedetails.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ram.moviedetails.model.Search

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieList")
    fun getMovie(): PagingSource<Int, Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(quotes: List<Search>)

    @Query("DELETE FROM MovieList")
    suspend fun deleteMovie()

}