package com.example.cinefy.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.componets.MovieCard
import com.example.compose.extendedLight

@Composable
fun ElemtListScreen(movies: List<Movie>, modifier: Modifier = Modifier) {
    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600

    var searchQuery by remember { mutableStateOf("") }
    val filteredMovies = movies.filter { movie ->
        movie.title.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Barra de búsqueda
        MedHeaderCompConBuscador(
            title = stringResource(R.string.listaDePeliculas),
            searchQuery = searchQuery,
            onQueryChange = { searchQuery = it })

        if (isExpanded) {
            // Pantalla expandida
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                content = {
                    items(filteredMovies) { movie ->
                        MovieCard(movie)
                    }
                }
            )
        } else {
            // Pantalla compacta
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                content = {
                    items(filteredMovies) { movie ->
                        MovieCard(movie)
                    }
                }
            )
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text(stringResource(R.string.search_placeholder)) }
    )
}

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    ElemtListScreen(DataSource.movieList())
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
            placeholder = { Text(stringResource(R.string.search_placeholder)) })
    }
}