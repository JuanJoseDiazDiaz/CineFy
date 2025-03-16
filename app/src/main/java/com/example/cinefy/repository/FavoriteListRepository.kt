package com.example.cinefy.repository

import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.localdatabase.MovieDao
import kotlinx.coroutines.flow.Flow

class FavoriteListRepository(
    private val listDAO: MovieDao,
): ListInterface {

    override suspend fun insert(movie: MovieEntity) = listDAO.insert(movie)
    suspend fun update(movieEntity: MovieEntity) = listDAO.updateMovie(movieEntity)
    override suspend fun delete(movieEntity: MovieEntity) = listDAO.deleteMovie(movieEntity)
    override val getAllCharacters: Flow<List<MovieEntity>> = listDAO.getAllMoviesFlow()
}