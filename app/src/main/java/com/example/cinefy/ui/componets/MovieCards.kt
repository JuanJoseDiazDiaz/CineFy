package com.example.cinefy.ui.componets

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinefy.R
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.datamodel.toMovieEntity
import com.example.cinefy.ui.movie.MovieViewModel
import com.example.cinefy.ui.screens.favoritelist.FavListScrenViewModel

@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit,
    movieViewModel: MovieViewModel, // Usamos el MovieViewModel para actualizar el estado
) {
    val context = LocalContext.current
    val alreadyFavorite = stringResource(R.string.already)
    val addedToFavorite = stringResource(R.string.addfavorite)

    val isFavorite = movie.isFavorite // Asegúrate de que el estado de favorito se pase correctamente

    Row {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onClick() },
            shape = MaterialTheme.shapes.medium,
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = movie.imageUrl,
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(150.dp)
                    )
                    Box {
                        Row {
                            IconButton(
                                onClick = {
                                    movieViewModel.toggleFavorite(movie)
                                    val message = if (isFavorite) {
                                        "Película eliminada de favoritos"
                                    } else {
                                        "Película añadida a favoritos"
                                    }
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()// Actualiza en el ViewModel
                                },
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
                            ) {
                                if (!isFavorite){
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        modifier = Modifier.size(48.dp),
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }else{
                                    Icon(
                                        imageVector = Icons.Default.Favorite ,
                                        modifier = Modifier.size(48.dp),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}







