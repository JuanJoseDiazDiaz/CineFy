package com.example.cinefy.ui.movie

import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity

data class MovieUiState(
    val movies: List<Movie> = emptyList(),
    val moviesEntitys: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite : Boolean = false,
)
enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
}