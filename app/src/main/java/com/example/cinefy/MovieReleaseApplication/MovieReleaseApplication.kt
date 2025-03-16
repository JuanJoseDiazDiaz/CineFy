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

val Context.dataStore by preferencesDataStore(name = UserPreferencesManager.SETTINGS_FILE)

class MovieReleaseApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesManager
    lateinit var listRepository: FavoriteListRepository
    lateinit var commentRepository: CommentRepository
    lateinit var moviesDao: MovieDao
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var userRepository: UserRepository

    //Contenedor de dependencias manuales que se usa por completo en la app
    override fun onCreate() {
        super.onCreate()
//Creación de la instancia del repositorio de preferencias de usuario
        userPreferencesRepository = UserPreferencesManager(dataStore)
        listRepository = FavoriteListRepository(MovieDatabase.getDatabase(this).moviesDAO())
        commentRepository = CommentRepository(MovieDatabase.getDatabase(this).commentsDAO())
        userRepository = UserRepository(MovieDatabase.getDatabase(this).userDAO())
        val database = MovieDatabase.getDatabase(this)
        moviesDao = database.moviesDAO()
        // Crear la fábrica del ViewModel
        viewModelFactory = MovieViewModel.Factory(
            userPreferencesRepository, moviesDao, listRepository, applicationContext, commentRepository,userRepository
        )
    }

}