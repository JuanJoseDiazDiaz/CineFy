package com.example.cinefy.ui.screens.favoritelist

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
import com.example.cinefy.localdatabase.MovieDao
import com.example.cinefy.repository.CommentRepository
import com.example.cinefy.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavListScrenViewModel(
    private val listRepository: FavoriteListRepository,
    private val commentRepository: CommentRepository,
    private val movieDao: MovieDao // Asegúrate de pasar el DAO
): ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieReleaseApplication)
                FavListScrenViewModel(
                    application.listRepository,
                    application.commentRepository,
                    application.moviesDao
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
    private fun recuperarFavoritos() {
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

    // Borrar un favorito de la base de datos
    fun borrarFavorito(item: MovieEntity) {
        viewModelScope.launch {
            try {
                listRepository.delete(item) // Usamos el repositorio para eliminar
                // Actualizar la lista de favoritos
                _uiState.value = _uiState.value.copy(
                    favorites = _uiState.value.favorites.filter { it.id != item.id }
                )
                // Sincronizar con MovieDao para eliminar en la otra parte
                movieDao.deleteMovie(item)
            } catch (e: Exception) {
                // Manejar el error al eliminar
                _uiState.value = _uiState.value.copy(errorMessageFAV = ErrorMessageFAV.ERROR_DELETING_FAV)
            }
        }
    }

    fun insertarFavorito(item: MovieEntity) {
        viewModelScope.launch {
            try {
                listRepository.insert(item)
                _uiState.value = _uiState.value.copy(
                    favorites = _uiState.value.favorites + item // Agregar a la lista de favoritos
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessageFAV = ErrorMessageFAV.ERROR_INSERTING_FAV)
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
                    content = commentText
                )
                commentRepository.insertComment(newComment)
            } catch (e: Exception) {
            }
        }
    }
    fun getCommentsForFavorite(favoriteName: String): Flow<List<Comment>> {
        return commentRepository.getCommentsByFavoriteId(favoriteName)
    }
    fun toggleFavorite(movie: MovieEntity) {
        viewModelScope.launch {
            try {
                // Cambia el estado de favorito
                val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)

                // Actualiza la base de datos
                listRepository.update(updatedMovie)

                // Recargar la lista de favoritos después de actualizar
                recuperarFavoritos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessageFAV = ErrorMessageFAV.ERROR_UPDATING_FAV
                )
            }
        }
    }
}