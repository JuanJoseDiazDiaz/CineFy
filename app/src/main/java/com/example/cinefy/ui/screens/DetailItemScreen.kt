package com.example.cinefy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.componets.MovieCardDetail
import com.example.cinefy.ui.movie.MovieViewModel

@Composable
fun DetailItemScreen(
    movieTitle: String?, // Recibe el ID de la película desde la navegación
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by movieViewModel.uiState.collectAsState()
    val movie = uiState.movies.find { it.title == movieTitle }
    val isLoading = uiState.isLoading
    val errorMessage = uiState.errorMessage

    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas
    LaunchedEffect(Unit) {
        movieViewModel.getMovies()
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MedHeaderCompDetail(title = stringResource(R.string.Detail_Item), navController)

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: $errorMessage")
                }
            }
            movie != null -> {
                MovieCardDetailWithFavButton(movie)
            }
            else -> {
                Text(text = stringResource(R.string.movie_not_found), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun MovieCardDetailWithFavButton(movie: Movie, movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory),) {
    var isFavorite by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { MovieCardDetail(movie, movieViewModel) }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Button(onClick = { isFavorite = !isFavorite }) {
                Text(
                    text = if (isFavorite) stringResource(R.string.AddConfirm) else stringResource(R.string.AddFav)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailItemScreenPreview() {
    // No se puede previsualizar directamente con NavController, se debe simular
}
