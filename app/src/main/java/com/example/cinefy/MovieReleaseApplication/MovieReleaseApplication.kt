package com.example.cinefy.MovieReleaseApplication

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.localdatabase.MovieDao
import com.example.cinefy.localdatabase.MovieDatabase
import com.example.cinefy.repository.CommentRepository
import com.example.cinefy.repository.FavoriteListRepository

val Context.dataStore by preferencesDataStore(name = UserPreferencesManager.SETTINGS_FILE)

class MovieReleaseApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesManager
    lateinit var listRepository: FavoriteListRepository
    lateinit var commentRepository: CommentRepository
    lateinit var moviesDao: MovieDao

    //Contenedor de dependencias manuales que se usa por completo en la app
    override fun onCreate() {
        super.onCreate()
//Creación de la instancia del repositorio de preferencias de usuario
        userPreferencesRepository = UserPreferencesManager(dataStore)
        listRepository = FavoriteListRepository(MovieDatabase.getDatabase(this).moviesDAO())
        commentRepository = CommentRepository(MovieDatabase.getDatabase(this).commentsDAO())
        val database = MovieDatabase.getDatabase(this)
        moviesDao = database.moviesDAO()
    }

}