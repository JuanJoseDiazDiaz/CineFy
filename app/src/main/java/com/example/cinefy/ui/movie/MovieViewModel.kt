package com.example.cinefy.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.data.RetrofitInstance
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.repository.MovieRepository
import com.example.cinefy.localdatabase.MovieDao
import com.example.cinefy.localdatabase.MovieDatabase
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.datamodel.toMovie
import com.example.cinefy.datamodel.toMovieEntity
import com.example.cinefy.repository.FavoriteListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(
    private val userPreferencesRepository: UserPreferencesManager,
    private val movieDao: MovieDao,
    private val favoriteListRepository: FavoriteListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()
    val favoriteMovies: LiveData<List<Movie>> = movieDao.getFavoriteMovies()
        .map { list -> list.map { it.toMovie() } }
        .asLiveData()

    private val repository: MovieRepository = MovieRepository(RetrofitInstance.api)
    var isRequestInProgress = false

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieReleaseApplication)
                val userPreferencesRepository = application.userPreferencesRepository
                val database = MovieDatabase.getDatabase(application)
                MovieViewModel(userPreferencesRepository, database.moviesDAO(), application.listRepository)
            }
        }
    }

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                withContext(Dispatchers.IO) {
                    val localMovies = movieDao.getAllMovies().map { it.toMovie() }
                    if (localMovies.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            _uiState.value = _uiState.value.copy(movies = localMovies, isLoading = false)
                        }
                    } else {
                        val movies = repository.getMovies()
                        val movieEntities = movies.map { it.toMovieEntity() }
                        movieDao.insertMovies(movieEntities)

                        withContext(Dispatchers.Main) {
                            _uiState.value = _uiState.value.copy(movies = movies, isLoading = false)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.IO) {
                    val localMovies = movieDao.getAllMovies().map { it.toMovie() }
                    withContext(Dispatchers.Main) {
                        _uiState.value = _uiState.value.copy(movies = localMovies, errorMessage = e.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun addComment(movieTitle: String, comment: Comment) {
        viewModelScope.launch {
            try {
                val movie = withContext(Dispatchers.IO) { movieDao.getMovieByTitle(movieTitle) }
                movie?.let {
                    val updatedComments = it.comments.toMutableList().apply { add(comment) }.toList()

                    withContext(Dispatchers.IO) {
                        movieDao.updateComments(movieTitle, updatedComments)
                    }

                    val updatedMovies = _uiState.value.movies.map { movieItem ->
                        if (movieItem.title == movieTitle) {
                            val movieEntity = movieDao.getMovieByTitle(movieTitle)
                            movieEntity?.copy(comments = updatedComments)?.toMovie() ?: movieItem
                        } else movieItem
                    }
                    _uiState.value = _uiState.value.copy(movies = updatedMovies)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Error al agregar comentario: ${e.message}")
            }
        }
    }

    // Agregar esta función para sincronizar los favoritos
    fun updateFavorites(movie: MovieEntity) {
        viewModelScope.launch {
            movieDao.updateMovie(movie)
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            try {
                // Cambiar el estado de la película en la base de datos
                val updatedMovieEntity = movie.toMovieEntity().copy(isFavorite = !movie.isFavorite)
                movieDao.updateMovie(updatedMovieEntity)

                // Actualizar la UI en el ViewModel
                val updatedMovies = _uiState.value.movies.map { movieItem ->
                    if (movieItem.id == movie.id) {
                        movieItem.copy(isFavorite = !movieItem.isFavorite)
                    } else movieItem
                }

                // Actualizar el estado con los cambios
                _uiState.value = _uiState.value.copy(movies = updatedMovies)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Error al actualizar favorito: ${e.message}")
            }
        }
    }

    // Eliminar película de favoritos y sincronizar con el repositorio
    fun removeMovie(movie: MovieEntity) {
        viewModelScope.launch {
            try {
                favoriteListRepository.delete(movie) // Usamos el repositorio para eliminar
                // Sincronizar en la UI
                val updatedMovies = _uiState.value.movies.filter { it.id != movie.id }
                _uiState.value = _uiState.value.copy(movies = updatedMovies)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Error al eliminar la película: ${e.message}")
            }
        }
    }


    fun resetDataUser() {
        _uiState.value = MovieUiState()
    }

    fun initializeUserData(name: String, password: String, email: String) {
        // Lógica para inicializar datos del usuario
    }
}






