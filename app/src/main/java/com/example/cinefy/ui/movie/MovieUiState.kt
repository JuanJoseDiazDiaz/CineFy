package com.example.cinefy.ui.movie

import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.model.MovieEntity

data class MovieUiState(
    val nameUser: String = "",
    val passwordUser: String = "",
    val emailUser: String = "",
    val movies: List<Movie> = emptyList(),
    val moviesEntitys: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
enum class UserMessage {
    ERROR_ACCESSING_DATASTORE,
    ERROR_WRITING_DATASTORE,
}