package com.example.cinefy.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // Inserta una lista de películas en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(movie: MovieEntity)

    // Obtiene todas las películas ordenadas por título
    @Query("SELECT * FROM movies_favorites ORDER BY title ASC")
    fun getAllMovies(): List<MovieEntity>
    // Obtiene todas las películas ordenadas por título
    @Query("SELECT * FROM movies_favorites ORDER BY title ASC")
    fun getAllMoviesFlow(): Flow<List<MovieEntity>>

    // Obtiene una película por su título
    @Query("SELECT * FROM movies_favorites WHERE title LIKE :title LIMIT 1")
    suspend fun getMovieByTitle(title: String): MovieEntity?

    // Elimina todas las películas de la base de datos
    @Query("DELETE FROM movies_favorites")
    suspend fun clearMovies()

    // Obtiene un número específico de películas aleatorias
    @Query("SELECT * FROM movies_favorites ORDER BY RANDOM() LIMIT :number")
    fun getSomeRandomMovies(number: Int): Flow<List<MovieEntity>>

    // Obtiene películas por género
    @Query("SELECT * FROM movies_favorites WHERE genres = :genre ORDER BY title ASC")
    fun getAllMoviesByGenre(genre: String): Flow<List<MovieEntity>>

    // Obtiene un número específico de películas aleatorias por género
    @Query("SELECT * FROM movies_favorites WHERE genres = :genre ORDER BY RANDOM() LIMIT :number")
    fun getSomeRandomMoviesByGenre(genre: String, number: Int): Flow<List<MovieEntity>>

    // Obtiene un número específico de películas aleatorias por género, pero de forma suspendida
    @Query("SELECT * FROM movies_favorites WHERE genres = :genre ORDER BY RANDOM() LIMIT :number")
    suspend fun getOnceSomeRandomMoviesByGenre(genre: String, number: Int): List<MovieEntity>

    // Obtiene un número específico de películas aleatorias por título, de forma suspendida
    @Query("SELECT * FROM movies_favorites WHERE title LIKE :title ORDER BY RANDOM() LIMIT :number")
    suspend fun getOnceSomeRandomMoviesByTitle(title: String, number: Int): List<MovieEntity>

    // Elimina una película específica de la base de datos
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies_favorites WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("UPDATE movies_favorites SET comments = :comments WHERE title = :title")
    suspend fun updateComments(title: String, comments: List<Comment>)

}