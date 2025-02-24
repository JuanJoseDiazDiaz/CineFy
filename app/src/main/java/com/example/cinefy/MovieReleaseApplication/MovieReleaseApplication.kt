package com.example.cinefy.MovieReleaseApplication

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.cinefy.data.UserPreferencesManager

val Context.dataStore by preferencesDataStore(name = UserPreferencesManager.SETTINGS_FILE)

class MovieReleaseApplication : Application(){
    lateinit var userPreferencesRepository: UserPreferencesManager
    //Contenedor de dependencias manuales que se usa por completo en la app
    override fun onCreate() {
        super.onCreate()
//Creación de la instancia del repositorio de preferencias de usuario
        userPreferencesRepository = UserPreferencesManager(dataStore)

    }

}