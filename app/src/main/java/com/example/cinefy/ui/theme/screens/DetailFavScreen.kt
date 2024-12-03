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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.componets.MovieCard
import com.example.compose.extendedLight
import com.google.ai.client.generativeai.type.content

@Composable
fun DetailFavScreen(movies: MutableList<Movie>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Uso de MedHeaderComp para la cabecera
        MedHeaderComp2(title = stringResource(R.string.DetailFavoritos))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(movies) { movie ->
                    MovieCard(movie)
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview2() {
    DetailFavScreen(DataSource.movieList())
}


val LocalExtendedColorScheme2 = staticCompositionLocalOf {
    extendedLight //tomar cualquiera de los creados como referencia.
}
@Composable
fun MedHeaderComp2(title: String) {
    val extendedColorScheme = LocalExtendedColorScheme2.current
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