package com.example.cinefy.repository

import com.example.cinefy.datamodel.MovieEntity
import kotlinx.coroutines.flow.Flow

interface ListInterface {
    suspend fun insert(movieEntity: MovieEntity)
    suspend fun delete(movieEntity: MovieEntity)
    val getAllCharacters: Flow<List<MovieEntity>>
}