package com.example.cinefy.ui.screens.movieElementList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
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
import com.example.cinefy.ui.componets.MovieCard
import com.example.cinefy.ui.movie.MovieViewModel
import com.example.cinefy.ui.screens.favoritelist.FavListScrenViewModel
import com.example.cinefy.ui.theme.extendedLight

/**
 * Implementacion por parameteros el viewModel para que se conecte entre si
 * */
@Composable
fun ElementListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory), // ViewModel para obtener datos
    favViewModel: FavListScrenViewModel = viewModel(factory = FavListScrenViewModel.Factory)
) {
    val uiState by movieViewModel.uiState.collectAsState()
    val movies = uiState.movies // Lista de películas obtenidas de la API

    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600

    var searchQuery by remember { mutableStateOf("") }
    val filteredMovies = movies.filter { it.title.contains(searchQuery, ignoreCase = true) }

    LaunchedEffect(Unit) {
        if (movies.isEmpty()) {
            movieViewModel.getMovies()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderCompConBuscador(
            title = stringResource(R.string.listaDePeliculas),
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        when {
            uiState.isLoading -> {
                // Muestra un indicador de carga mientras se obtienen los datos
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                // Muestra un mensaje de error si la API falla
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${uiState.errorMessage}")
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    if (filteredMovies.isNotEmpty()) {
                        items(filteredMovies) { movie ->
                            MovieCard(movie = movie, onClick = {
                                navController.navigate("details_fav/${movie.title}")
                            }, movieViewModel)
                        }
                    } else {
                        item {
                            Text(text = "No hay películas disponibles", modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
//    ElementListScreen(DataSource.movieList())
}

val LocalExtendedColorScheme = staticCompositionLocalOf {
    extendedLight //tomar cualquiera de los creados como referencia.
}

@Composable
fun MedHeaderComp(title: String) {
    val extendedColorScheme = LocalExtendedColorScheme.current
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = extendedColorScheme.customHeader.color,
        contentColor = extendedColorScheme.customHeader.onColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun MedHeaderCompConBuscador(title: String, searchQuery: String, onQueryChange: (String) -> Unit) {
    var isSearching by remember { mutableStateOf(false) }
    val extendedColorScheme = LocalExtendedColorScheme.current
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = extendedColorScheme.customHeader.color,
        contentColor = extendedColorScheme.customHeader.onColor
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(onClick = {
                    isSearching = !isSearching
                }, modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                }
            }
        }
    }
    if (isSearching) {
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            placeholder = { Text(stringResource(R.string.search_placeholder)) }
        )
    }
}

@Composable
fun MedHeaderCompDetail(title: String, navController: NavController) {
    val extendedColorScheme = LocalExtendedColorScheme.current
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = extendedColorScheme.customHeader.color,
        contentColor = extendedColorScheme.customHeader.onColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
