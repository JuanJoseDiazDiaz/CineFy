package com.example.cinefy.ui.screens.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.data.UserPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val userPreferencesManager: UserPreferencesManager) :
    ViewModel() {

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieReleaseApplication)
                ProfileViewModel (application.userPreferencesRepository)
            }
        }
    }

    //Estado de la interfaz de usuario.
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState : StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesManager.userPrefs
                .catch { e->
                    _uiState.update {
                            currentState ->
                        currentState.copy(errorMessage = UserMessage.ERROR_ACCESSING_DATASTORE.toString())
                    }
                }.collect(){
                        preferences ->
                    _uiState.update { currentState->
                        currentState.copy(
                            modoDeVisualizacionPantalla = try {
                                ModoVisualizacionPantalla.valueOf(preferences.modoDeVisualizacionPantalla.toString())
                            }catch (e : Exception){
                                ModoVisualizacionPantalla.SISTEMA
                            }
                        )
                    }
                }
        }
    }

    fun setSettings(modoVisualizacionPantalla: ModoVisualizacionPantalla) {
        viewModelScope.launch {
            try {
                userPreferencesManager.saveTheme(modoVisualizacionPantalla.modoVisualizacion)
                _uiState.update { currentState ->
                    currentState.copy(modoDeVisualizacionPantalla = modoVisualizacionPantalla)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(errorMessage = UserMessage.ERROR_WRITING_DATASTORE.toString())
                }
            }
        }
    }

}