package com.example.cinefy.ui.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.model.toMovieEntity
import com.example.cinefy.ui.movie.MovieViewModel

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit, movieViewModel: MovieViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Row {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onClick() },
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Si movie.imageUrl es una URL, usar Coil para cargar la imagen
                    AsyncImage(
                        model = movie.imageUrl, // Asume que movie.imageUrl es una URL
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(150.dp) // Ajusta el tamaño de la imagen según lo necesites
                    )
                    Box {
                        Row {
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDialog = false },
                                    title = { Text(text = "Información") },
                                    text = { Text(text = "Este elemento ya está guardado como favorito.") },
                                    confirmButton = {
                                        TextButton(onClick = { showDialog = false }) {
                                            Text(text = "OK")
                                        }
                                    }
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (movie.toMovieEntity().isFavorite) {
                                        showDialog = true
                                    } else {
                                        movieViewModel.toggleFavorite(movie)
                                    }
                                },
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
                            ) {
                                Icon(
                                    imageVector = if (movie.toMovieEntity().isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    modifier = Modifier.size(40.dp),
                                    contentDescription = if (movie.toMovieEntity().isFavorite) "Remove from favorites" else "Add to favorites",
                                    tint = if (movie.toMovieEntity().isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


