package com.example.cinefy.repository

import com.example.cinefy.datamodel.SingIn
import com.example.cinefy.localdatabase.UserDAO

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