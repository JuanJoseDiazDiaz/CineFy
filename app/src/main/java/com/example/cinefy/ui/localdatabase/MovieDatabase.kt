package com.example.cinefy.ui.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinefy.ui.model.MovieEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){
abstract fun moviesDAO() : MovieDao
    companion object{
        @Volatile
        private var Instance: MovieDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getDataBases(context: Context): MovieDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "cinefy_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}