package com.example.cinefy.ui.movie

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    // Datos a guardar nombre de usuario, contraseña y correo electrónico
    private var nameUser: String? = null
    private var passwordUser: String? = null
    private var emailUser: String? = null

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