package com.example.cinefy.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import coil.compose.AsyncImage
import com.example.cinefy.R
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.toMovieEntity
import com.example.cinefy.ui.movie.MovieViewModel

@Composable
fun MovieCardDetail(movie: Movie, movieViewModel: MovieViewModel) {
    var isTooltipVisible by remember { mutableStateOf(false) }
    var commentAuthor by remember { mutableStateOf("") }
    var commentContent by remember { mutableStateOf("") }
    var showCommentDialog by remember { mutableStateOf(false) }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = movie.imageUrl, // Asume que movie.imageUrl es una URL
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(500.dp).height(500.dp) // Ajusta el tamaño de la imagen según lo necesites
            )
        }
    }
    StandardTextComp(
        text = "${movie.title}",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(8.dp)
    )
    Box {
        Button(onClick = { isTooltipVisible = true }) {
            Text(stringResource(R.string.VerDetalles))
        }
        if (isTooltipVisible) {
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { isTooltipVisible = false }
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Column {
                        StandardTextComp(
                            text = "Genero: ${movie.genres.joinToString(", ")}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Rank: ${movie.rank}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Fecha de Estreno: ${movie.year}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Descripcion: ${movie.description}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
    Button(onClick = { showCommentDialog = true }) {
        Text(text = "Añadir Comentario")
    }
    if (showCommentDialog) {
        AlertDialog(
            onDismissRequest = { showCommentDialog = false },
            title = { Text(text = "Añadir Comentario") },
            text = {
                Column {
                    TextField(
                        value = commentAuthor,
                        onValueChange = { commentAuthor = it },
                        label = { Text(text = "Autor") }
                    )
                    TextField(
                        value = commentContent,
                        onValueChange = { commentContent = it },
                        label = { Text(text = "Comentario") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
//                    movieViewModel.addComment(movie.title, Comment(commentAuthor, commentContent))
                    showCommentDialog = false
                }) {
                    Text(text = "Añadir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCommentDialog = false }) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
    Column {
        Text(text = "Comentarios", style = MaterialTheme.typography.headlineMedium)
        val movieEntity = movieViewModel.uiState.collectAsState().value.movies.firstOrNull { it.title == movie.title }?.toMovieEntity()
        movieEntity?.comments?.forEach { comment ->
            Text(text = "${comment.author}: ${comment.content}")
        }
    }
}

