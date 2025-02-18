package com.example.cinefy.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinefy.data.RetrofitInstance
import com.example.cinefy.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    // Datos a guardar nombre de usuario, contraseña y correo electrónico
    private var nameUser: String? = null
    private var passwordUser: String? = null
    private var emailUser: String? = null

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMovies()
                _movies.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val movies = repository.getMovies()
                _uiState.value = MovieUiState(movies = movies)
            } catch (e: Exception) {
                _uiState.value = MovieUiState(errorMessage = e.message)
            }
        }
    }

    // Reseteo de datos del usuario
    fun resetDataUser() {
        // Verifica que los valores no sean null antes de actualizar el estado
        _uiState.value = MovieUiState(
            nameUser = nameUser ?: "Usuario Desconocido",  // Valor por defecto si es null
            passwordUser = passwordUser ?: "Contraseña No Establecida",  // Valor por defecto si es null
            emailUser = emailUser ?: "Email No Establecido"  // Valor por defecto si es null
        )
    }

    // Inicializa el ViewModel con valores si están disponibles
    fun initializeUserData(name: String, password: String, email: String) {
        nameUser = name
        passwordUser = password
        emailUser = email
        resetDataUser()  // Actualiza el estado después de la inicialización
    }

    // Inicializa el estado con valores predeterminados
    init {
        resetDataUser()  // Llama al reset de datos si no tienes valores disponibles
    }
}