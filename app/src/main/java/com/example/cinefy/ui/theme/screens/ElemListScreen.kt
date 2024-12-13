package com.example.cinefy.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
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
fun ElemtListScreen(movies: MutableList<Movie>, modifier: Modifier = Modifier) {
    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas

    Column(modifier = modifier.fillMaxSize()) {
        // Uso de MedHeaderComp para la cabecera
        MedHeaderComp(title = stringResource(R.string.listaDePeliculas))

        if (isExpanded) {
            // Pantalla expandida
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                content = {
                    items(movies) { movie ->
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
                    items(movies) { movie ->
                        MovieCard(movie)
                    }
                }
            )
        }
    }
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