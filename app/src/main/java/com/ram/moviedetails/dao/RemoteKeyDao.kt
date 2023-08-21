package com.ram.moviedetails.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ram.moviedetails.model.MovieRemoteKey


@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM movieremotekey WHERE id =:id")
    suspend fun getRemoteKeys(id: String): MovieRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MovieRemoteKey>)

    @Query("DELETE FROM movieremotekey")
    suspend fun deleteAllRemoteKeys()
}