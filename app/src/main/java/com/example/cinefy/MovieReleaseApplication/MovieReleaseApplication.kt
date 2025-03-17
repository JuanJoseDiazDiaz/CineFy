package com.example.cinefy.MovieReleaseApplication

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.localdatabase.MovieDao
import com.example.cinefy.localdatabase.MovieDatabase
import com.example.cinefy.repository.CommentRepository
import com.example.cinefy.repository.FavoriteListRepository
import com.example.cinefy.repository.UserRepository
import com.example.cinefy.ui.movie.MovieViewModel

// Singleton de DataStore para evitar conflictos
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class MovieReleaseApplication : Application() {

    // Dependencias
    lateinit var userPreferencesRepository: UserPreferencesManager
    lateinit var listRepository: FavoriteListRepository
    lateinit var commentRepository: CommentRepository
    lateinit var moviesDao: MovieDao
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        // Instancia única de UserPreferencesManager con DataStore
        userPreferencesRepository = UserPreferencesManager.getInstance(dataStore)

        // Base de datos y repositorios
        val database = MovieDatabase.getDatabase(this)
        moviesDao = database.moviesDAO()
        listRepository = FavoriteListRepository(moviesDao)
        commentRepository = CommentRepository(database.commentsDAO())
        userRepository = UserRepository(database.userDAO())

        // Configuración de ViewModel Factory
        viewModelFactory = MovieViewModel.Factory(
            userPreferencesRepository,
            moviesDao,
            listRepository,
            applicationContext,
            commentRepository,
            userRepository
        )
    }
}