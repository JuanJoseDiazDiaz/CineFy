package com.example.cinefy.interfaces

import com.example.cinefy.ui.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieInterfaces {
    // Inserta una lista de películas
    suspend fun insertMovies(movies: MovieEntity)

    // Actualiza una película
    suspend fun updateMovie(movie: MovieEntity)

    // Elimina una película
    suspend fun deleteMovie(movie: MovieEntity)

    // Elimina todas las películas
    suspend fun clearMovies()

    // Obtiene todas las películas
    val getAllMovies: Flow<List<MovieEntity>>

    // Obtiene un número específico de películas aleatorias
    val getSomeRandomMovies: (Int) -> Flow<List<MovieEntity>>

    // Obtiene todas las películas por género
    val getAllMoviesByGenre: (String) -> Flow<List<MovieEntity>>

    // Obtiene un número específico de películas aleatorias por género
    val getSomeRandomMoviesByGenre: (String, Int) -> Flow<List<MovieEntity>>

    // Obtiene un número específico de películas aleatorias por género de forma sincrónica
    suspend fun getOnceSomeRandomMoviesByGenre(genre: String, number: Int): List<MovieEntity>

    // Obtiene un número específico de películas aleatorias por título de forma sincrónica
    suspend fun getOnceSomeRandomMoviesByTitle(title: String, number: Int): List<MovieEntity>
}