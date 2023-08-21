package com.ram.moviedetails.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ram.moviedetails.dao.MovieDao
import com.ram.moviedetails.dao.RemoteKeyDao
import com.ram.moviedetails.model.MovieRemoteKey
import com.ram.moviedetails.model.Search


@Database(entities = [Search::class, MovieRemoteKey::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MovieDatabase::class.java, "MovieDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}