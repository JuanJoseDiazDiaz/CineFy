package com.example.cinefy.ui.movie

import com.example.cinefy.ui.model.Movie

data class MovieUiState(
    val nameUser: String = "",
    val passwordUser: String = "",
    val emailUser: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)