package com.example.cinefy.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinefy.datamodel.Comment
import kotlinx.coroutines.flow.Flow
@Dao
interface CommentDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertComment(comment: Comment)


    @Query("SELECT * FROM comments WHERE userName = :userName")
    fun getCommentsByFavoriteName(userName: String): Flow<List<Comment>>
}