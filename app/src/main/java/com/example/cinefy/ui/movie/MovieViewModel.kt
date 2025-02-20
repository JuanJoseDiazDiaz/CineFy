package com.example.cinefy.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinefy.data.RetrofitInstance
import com.example.cinefy.repository.MovieRepository
import com.example.cinefy.ui.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val repository: MovieRepository = MovieRepository(RetrofitInstance.api)
    var isRequestInProgress = false

    init {
        // Llamar a getMovies directamente desde el init para cargar los datos
        getMovies()
    }

    fun getMovies() {
        // Solo hace la petición si no hay otra en curso
        if (isRequestInProgress) return

        isRequestInProgress = true
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)  // Muestra el cargador
            try {
                // Realiza la solicitud a la API usando el repositorio
                val movies = repository.getMovies()  // Obtiene las películas
                // Actualiza el estado con las películas obtenidas
                _uiState.value = MovieUiState(movies = movies)
            } catch (e: Exception) {
                // Maneja los errores y actualiza el estado de la UI con el mensaje de error
                _uiState.value = MovieUiState(errorMessage = e.message)
            } finally {
                isRequestInProgress = false
            }
        }
    }

    // Reseteo de datos del usuario
    fun resetDataUser() {
        _uiState.value = MovieUiState(
//            nameUser = nameUser ?: "Usuario Desconocido",
//            passwordUser = passwordUser ?: "Contraseña No Establecida",
//            emailUser = emailUser ?: "Email No Establecido"
        )
    }

    // Inicializa el ViewModel con valores si están disponibles
    fun initializeUserData(name: String, password: String, email: String) {
//        nameUser = name
//        passwordUser = password
//        emailUser = email
//        resetDataUser()
    }

    // Inicializa el estado con valores predeterminados
    init {
        resetDataUser()
    }
}
