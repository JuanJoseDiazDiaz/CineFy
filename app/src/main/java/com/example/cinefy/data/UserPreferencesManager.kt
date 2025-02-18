package com.example.cinefy.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para obtener el DataStore en la aplicación
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesManager(private val context: Context) {

    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val THEME_KEY = stringPreferencesKey("theme") // "light", "dark" o "system"
    }

    // Flow para obtener el nombre del usuario
    val usernameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    // Flow para obtener el tema guardado
    val themeFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "system"
    }

    // Guardar el nombre del usuario
    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    // Guardar el tema (modo claro, oscuro o sistema)
    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
}