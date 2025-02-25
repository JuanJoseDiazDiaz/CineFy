package com.example.cinefy.ui.localdatabase

import android.content.Context
import androidx.room.*
import com.example.cinefy.ui.model.MovieEntity
import com.example.cinefy.ui.localdatabase.Converters  // Importa la clase de conversión

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // ⚠️ Agregar esto para manejar List<String>
abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDAO(): MovieDao

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
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
