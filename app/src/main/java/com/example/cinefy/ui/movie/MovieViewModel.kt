package com.example.cinefy.ui.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.data.RetrofitInstance
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.repository.MovieRepository
import com.example.cinefy.ui.localdatabase.MovieDao
import com.example.cinefy.ui.localdatabase.MovieDatabase
import com.example.cinefy.ui.model.toMovie
import com.example.cinefy.ui.model.toMovieEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieViewModel(
    private val userPreferencesRepository: UserPreferencesManager,
    private val movieDao: MovieDao // Se agrega DAO para acceso a Room
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val repository: MovieRepository = MovieRepository(RetrofitInstance.api)
    var isRequestInProgress = false

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as MovieReleaseApplication
                val userPreferencesRepository = application.userPreferencesRepository
                val database = MovieDatabase.getDatabase(application)
                MovieViewModel(userPreferencesRepository, database.moviesDAO())
            }
        }
    }

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Intentamos obtener películas de la API
                val movies = repository.getMovies()
                Log.d("API Response", "Movies: $movies")

                // Guardar en la base de datos local
                movieDao.clearMovies()
                movieDao.insertMovies(movies.map { it.toMovieEntity() }) // Convertimos a MovieEntity

                _uiState.value = _uiState.value.copy(movies = movies, isLoading = false)
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")

                // Si la API falla, intentamos cargar desde la base de datos local
                val localMovies = movieDao.getAllMovies().map { it.toMovie() } // Convertimos a Movie
                _uiState.value = _uiState.value.copy(movies = localMovies, errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun resetDataUser() {
        _uiState.value = MovieUiState()
    }

    fun initializeUserData(name: String, password: String, email: String) {
        // Lógica para inicializar datos del usuario
    }
}

