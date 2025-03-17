package com.example.cinefy.data

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cinefy.ui.screens.profileScreen.ModoVisualizacionPantalla
import com.example.cinefy.ui.screens.profileScreen.ProfileUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesManager private constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val IS_REGISTERED_KEY = booleanPreferencesKey("is_registered")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val THEME_KEY = stringPreferencesKey("theme") // "light", "dark" o "system"

        @Volatile
        private var INSTANCE: UserPreferencesManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferencesManager {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferencesManager(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    // Obtener flujo de nombre de usuario
    val usernameFlow: Flow<String?> = dataStore.data.map { preferences ->
        val username = preferences[USERNAME_KEY]
        Log.d("UserPreferencesManager", "Nombre de usuario capturado: $username")
        username
    }

    val themeFlow: Flow<ModoVisualizacionPantalla> = dataStore.data.map { preferences ->
        when (preferences[THEME_KEY] ?: "SYSTEM") {
            "DARK" -> ModoVisualizacionPantalla.OSCURO
            "LIGHT" -> ModoVisualizacionPantalla.CLARO
            else -> ModoVisualizacionPantalla.SISTEMA
        }
    }

    // Obtener estado de registro
    val isRegisteredFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[IS_REGISTERED_KEY] ?: false }

    // Guardar estado de registro
    suspend fun saveUserRegistered(isRegistered: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_REGISTERED_KEY] = isRegistered
        }
    }

    // Cerrar sesión
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[IS_REGISTERED_KEY] = false // Cambia el estado para volver a login
        }
    }

    // Guardar el nombre del usuario
    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    // Guardar la contraseña
    suspend fun savePassword(password: String) {
        dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    // Guardar el tema (modo claro, oscuro o sistema)
    suspend fun saveTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    // Función para obtener color dinámico basado en el tema
    @Composable
    fun dynamicTextColor(userPreferences: UserPreferencesManager): androidx.compose.ui.graphics.Color {
        val selectedTheme = userPreferences.themeFlow.collectAsState(initial = "system") // Por defecto, basado en el sistema

        return when (selectedTheme.value) {
            "light" -> androidx.compose.ui.graphics.Color.Black
            "dark" -> androidx.compose.ui.graphics.Color.White
            else -> if (isSystemInDarkTheme()) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black
        }
    }
    val userPrefs: Flow<ProfileUiState> = dataStore.data
        .catch {
            if (it is java.io.IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            val modoVisualizacion = preferences[THEME_KEY] ?: ModoVisualizacionPantalla.SISTEMA.modoVisualizacion
            ProfileUiState(modoVisualizacion)
        }
}
