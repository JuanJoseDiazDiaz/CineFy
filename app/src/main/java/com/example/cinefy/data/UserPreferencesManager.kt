package com.example.cinefy.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        val SETTINGS_FILE: String = "user_prefs"
        val USERNAME_KEY = stringPreferencesKey("username")
        val THEME_KEY = stringPreferencesKey("theme") // "light", "dark" o "system"
    }

    // Flow para obtener el nombre del usuario
    val usernameFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }

    // Flow para obtener el tema guardado
    val themeFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "system"
    }

    // Guardar el nombre del usuario
    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    // Guardar el tema (modo claro, oscuro o sistema)
    suspend fun saveTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }
}
