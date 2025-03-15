package com.example.cinefy.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinefy.datamodel.Comment
import kotlinx.coroutines.flow.Flow
@Dao
interface CommentDAO {

    @Insert
    suspend fun insertComment(comment: Comment)


    // Obtener todos los comentarios de un personaje específico
    @Query("SELECT * FROM comments WHERE NombreFavo = :favoriteName")
    suspend fun obtenerComentariosPorFavorito(favoriteName: String): List<Comment>

    // Obtener todos los comentarios
    @Query("SELECT * FROM comments")
    suspend fun obtenerTodosComentarios(): List<Comment>
}