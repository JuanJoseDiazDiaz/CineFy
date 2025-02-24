package com.example.cinefy.ui.componets


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.example.cinefy.data.DataCinefy
import com.example.cinefy.ui.model.Movie


@Composable
fun MovieCard(Movie: Movie, onClick: () -> Unit) {
    var fav_Movie by remember { mutableStateOf(false) }
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
                    // Si Movie.imageUrl es una URL, usar Coil para cargar la imagen
                    AsyncImage(
                        model = Movie.imageUrl, // Asume que Movie.imageUrl es una URL
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(150.dp) // Ajusta el tamaño de la imagen según lo necesites
                    )
                    Box {
                        Row {
                            IconButton(
                                onClick = { fav_Movie = !fav_Movie },
                                modifier = Modifier.size(48.dp),
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Transparent)
                            ) {
                                Icon(
                                    imageVector = Icons.TwoTone.Favorite,
                                    modifier = Modifier.size(40.dp),
                                    contentDescription = stringResource(R.string.more_content_desc),
                                    tint = if (fav_Movie) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

