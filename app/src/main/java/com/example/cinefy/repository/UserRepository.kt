package com.example.cinefy.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.datamodel.SingIn
import com.example.cinefy.localdatabase.UserDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserRepository(private val userDAO: UserDAO) {
    suspend fun insertarUsuario(user: SingIn){
        userDAO.insertUser(user)
    }

    suspend fun getUserByName(nameUser: String): SingIn? {
       return userDAO.getUserByName(nameUser)
    }

    suspend fun getUserByPassword(passwordUser: String): SingIn? {
        return userDAO.getUserByPassword(passwordUser)
    }
}