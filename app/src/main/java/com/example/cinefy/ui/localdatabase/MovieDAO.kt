package com.example.cinefy.ui.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinefy.ui.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    // Inserta una lista de películas en la base de datos
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovies(movies: List<Movie>)

    // Obtiene todas las películas ordenadas por título
    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAllMovies(): Flow<List<Movie>>

    // Obtiene una película por su título
    @Query("SELECT * FROM movies WHERE title LIKE :title LIMIT 1")
    suspend fun getMovieByTitle(title: String): Movie?

    // Elimina todas las películas de la base de datos
    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    // Obtiene un número específico de películas aleatorias
    @Query("SELECT * FROM movies ORDER BY RANDOM() LIMIT :number")
    fun getSomeRandomMovies(number: Int): Flow<List<Movie>>

    // Obtiene películas por género
    @Query("SELECT * FROM movies WHERE genres = :genre ORDER BY title ASC")
    fun getAllMoviesByGenre(genre: String): Flow<List<Movie>>

    // Obtiene un número específico de películas aleatorias por género
    @Query("SELECT * FROM movies WHERE genres = :genre ORDER BY RANDOM() LIMIT :number")
    fun getSomeRandomMoviesByGenre(genre: String, number: Int): Flow<List<Movie>>

    // Obtiene un número específico de películas aleatorias por género, pero de forma suspendida
    @Query("SELECT * FROM movies WHERE genres = :genre ORDER BY RANDOM() LIMIT :number")
    suspend fun getOnceSomeRandomMoviesByGenre(genre: String, number: Int): List<Movie>

    // Obtiene un número específico de películas aleatorias por título, de forma suspendida
    @Query("SELECT * FROM movies WHERE title LIKE :title ORDER BY RANDOM() LIMIT :number")
    suspend fun getOnceSomeRandomMoviesByTitle(title: String, number: Int): List<Movie>
}