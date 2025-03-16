package com.example.cinefy.localdatabase

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.datamodel.SingIn

@Database(entities = [Comment::class, MovieEntity::class, SingIn::class], version = 14, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MovieDao
    abstract fun commentsDAO(): CommentDAO
    abstract fun userDAO(): UserDAO


    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "cinefy_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("MovieDatabase", "Database created")
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
