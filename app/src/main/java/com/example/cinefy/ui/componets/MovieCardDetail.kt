package com.example.cinefy.ui.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.Movie
//import com.example.cinefy.datamodel.toMovieEntity
import com.example.cinefy.ui.screens.movieElementList.MovieViewModel

@Composable
fun MovieCardDetail(movie: Movie, movieViewModel: MovieViewModel, onAddComment: (String) -> Unit, commentsList: List<Comment>) {
    var showCommentField by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    val uiStateProfile = movieViewModel.uiStateProfile
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = movie.imageUrl, // Asume que movie.imageUrl es una URL
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(300.dp)
                .height(300.dp) // Ajusta el tamaño de la imagen según lo necesites
        )
        StandardTextComp(
            text = "${movie.title}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(5.dp)
        )

        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(5.dp)
            ) {
                StandardTextComp(
                    text = "Género: ${movie.genres.joinToString(", ")}",
                    style = MaterialTheme.typography.headlineSmall,
                )
                StandardTextComp(
                    text = "Descripción: ",
                    style = MaterialTheme.typography.headlineSmall,
                )
                StandardTextComp(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column {
            Button(onClick = { showCommentField = !showCommentField }) {
                Text(text = if (showCommentField) "Cancelar" else "Agregar Comentario")
            }
            if (showCommentField) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text("Escribe tu comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            onAddComment(commentText)
                            commentText = ""
                            showCommentField = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enviar")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Comentarios:",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (commentsList.isEmpty()) {
                Text(
                    text = "No hay comentarios aún.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    fontSize = 25.sp
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Asegura espacio visible

                ) {
                    itemsIndexed(commentsList) { index, comentario ->
                        CommentItem(comment = comentario)
                        if (index < commentsList.size - 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }



            }
        }


    }
}


@Composable
fun CommentItem(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = comment.author, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = comment.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


