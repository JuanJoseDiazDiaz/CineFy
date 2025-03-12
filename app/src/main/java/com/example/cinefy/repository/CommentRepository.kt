package com.example.cinefy.repository

import com.example.cinefy.datamodel.Comment
import com.example.cinefy.localdatabase.CommentDAO
import com.example.cinefy.localdatabase.MovieDao
import kotlinx.coroutines.flow.Flow

class CommentRepository(private val commentDAO: CommentDAO) {

    suspend fun insertComment(comment: Comment) {
        commentDAO.insertComment(comment)
    }

    fun getCommentsByFavoriteId(favoriteName: String): Flow<List<Comment>> {
        return commentDAO.getCommentsByFavoriteName(favoriteName)
    }
}