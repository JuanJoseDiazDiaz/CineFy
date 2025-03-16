package com.example.cinefy.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cinefy.datamodel.SingIn

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: SingIn)

    @Query("SELECT * FROM users WHERE userName = :nameUser LIMIT 1")
    suspend fun getUserByName(nameUser: String): SingIn?

    @Query("SELECT * FROM users WHERE password = :passwordUser LIMIT 1")
    suspend fun getUserByPassword(passwordUser: String): SingIn?
}