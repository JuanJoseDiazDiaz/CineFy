package com.example.cinefy.ui.screens.favoritelist

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.datamodel.SingIn
import com.example.cinefy.datamodel.toMovie
import com.example.cinefy.localdatabase.MovieDao
import com.example.cinefy.repository.CommentRepository
import com.example.cinefy.repository.FavoriteListRepository
import com.example.cinefy.ui.movie.MovieViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavListScrenViewModel(
    private val listRepository: FavoriteListRepository,
    private val commentRepository: CommentRepository,
    private val movieDao: MovieDao,
    private val movieViewModel: MovieViewModel
): ViewModel() {

    companion object {
        fun Factory(
            application: Application,
            movieViewModel: MovieViewModel
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = application as MovieReleaseApplication
                FavListScrenViewModel(
                    app.listRepository,
                    app.commentRepository,
                    app.moviesDao,
                    movieViewModel
                )
            }
        }
    }
    private val _uiState = MutableStateFlow(FavListScreenUiState())
    val uiState: StateFlow<FavListScreenUiState> = _uiState.asStateFlow()

    init {
        recuperarFavoritos()
    }

    // Recuperar los favoritos de la base de datos
    fun recuperarFavoritos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            listRepository.getAllCharacters
                .catch { e ->
                    // Aquí gestionamos el error
                    emit(emptyList<MovieEntity>())
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessageFAV = ErrorMessageFAV.ERROR_LOADING_FAVLIST // Usamos el enum o clase de errores
                    )
                }
                .collect { favList ->
                    _uiState.value = _uiState.value.copy(
                        favorites = favList.filter { it.isFavorite }, // Mostrar solo favoritos
                        isLoading = false,
                        errorMessageFAV = null // No hay error
                    )
                }
        }
    }

    fun borrarFavorito(movieEntity: MovieEntity) {
        viewModelScope.launch {
            try {
                // Cambiar el estado de la película en la base de datos para marcarla como no favorita
                val updatedMovie = movieEntity.copy(isFavorite = false)

                // Actualizar la base de datos, pero no eliminar la película
                listRepository.update(updatedMovie)


                // Actualizar la lista de películas favoritas en la UI
                _uiState.update { currentState ->
                    currentState.copy(
                        favorites = currentState.favorites.filter { it.id != movieEntity.id }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessageFAV = ErrorMessageFAV.ERROR_UPDATING_FAV
                )
            }
        }
    }





    fun borrarMensajesError() {
        _uiState.value = _uiState.value.copy(
            errorMessageFAV = null
        )
    }
    fun addCommentToFavorite(favoriteName: String?, userName: String, commentText: String) {
        viewModelScope.launch {
            try {
                val newComment = Comment(
                    author = userName,
                    favoriteName = favoriteName,
                    content = commentText
                )
                commentRepository.insertarComentario(newComment)
            } catch (e: Exception) {
            }
        }
    }
    suspend fun getCommentsForFavorite(favoriteName: String): List<Comment> {
        return commentRepository.obtenerComentariosPorFavorito(favoriteName)
    }
    fun toggleFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            try {
                // Cambiar el estado de la película de favorita a no favorita (solo en la base de datos)
                val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)

                // Actualiza la base de datos
                listRepository.update(updatedMovie)

                // Recargar la lista de favoritos para que se refleje el cambio visual
                recuperarFavoritos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessageFAV = ErrorMessageFAV.ERROR_UPDATING_FAV
                )
            }
        }
    }
}