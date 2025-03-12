package com.example.cinefy.ui.screens.favoritelist

import com.example.cinefy.datamodel.MovieEntity

data class FavListScreenUiState(
    val favorites: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessageFAV: ErrorMessageFAV? = null
)
enum class ErrorMessageFAV {
    ERROR_LOADING_FAVLIST,
    ERROR_INSERTING_FAV,
    ERROR_DELETING_FAV,
    ERROR_UPDATING_FAV
}