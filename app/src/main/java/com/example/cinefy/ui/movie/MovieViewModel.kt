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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val userPreferencesRepository: UserPreferencesManager) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val repository: MovieRepository = MovieRepository(RetrofitInstance.api)
    var isRequestInProgress = false

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Asegúrate de que estás accediendo a la aplicación correctamente
                val application = this[APPLICATION_KEY] as MovieReleaseApplication
                val userPreferencesRepository = application.userPreferencesRepository
                MovieViewModel(userPreferencesRepository)
            }
        }
    }

    init {
        // Llamar a getMovies directamente desde el init para cargar los datos
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val movies = repository.getMovies()
                Log.d("API Response", "Movies: $movies") // 🔍 Verifica que se están obteniendo datos
                _uiState.value = _uiState.value.copy(movies = movies, isLoading = false)
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}") // 📌 Muestra si hay error
                _uiState.value = _uiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    // Reseteo de datos del usuario
    fun resetDataUser() {
        _uiState.value = MovieUiState(
            // Datos de usuario a restablecer si es necesario
        )
    }

    // Inicializa el ViewModel con valores si están disponibles
    fun initializeUserData(name: String, password: String, email: String) {
        // Lógica para inicializar datos del usuario
    }
}
