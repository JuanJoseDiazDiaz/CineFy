package com.example.cinefy.repository

import com.example.cinefy.datamodel.Comment
import com.example.cinefy.localdatabase.CommentDAO
import com.example.cinefy.localdatabase.MovieDao
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val commentDAO: CommentDAO) {

    // Insertar un nuevo comentario
    suspend fun insertarComentario(comentario: Comment) {
        commentDAO.insertComment(comentario)
    }

    // Obtener todos los comentarios de un personaje favorito
    suspend fun obtenerComentariosPorFavorito(favoriteName: String): List<Comment> {
        return commentDAO.obtenerComentariosPorFavorito(favoriteName)
    }

    // Obtener todos los comentarios
    suspend fun obtenerTodosComentarios(): List<Comment> {
        return commentDAO.obtenerTodosComentarios()
    }
}