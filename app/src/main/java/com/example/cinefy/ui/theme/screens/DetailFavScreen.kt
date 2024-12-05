package com.example.cinefy.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.componets.ImageComp
import com.example.cinefy.ui.theme.componets.MovieCardDetail

class DetailFavScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DetailFavScreenContent(
                movie = DataSource.movieList().first(),
                comments = listOf("Genial película!", "Me encantó la trama.")
            )
        }
    }
}

@Composable
fun DetailFavScreenContent(movie: Movie, comments: List<String>, modifier: Modifier= Modifier) {
    var commentList by remember { mutableStateOf(comments) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Lógica para añadir un nuevo comentario (puedes reemplazar esto por un diálogo de entrada)
                commentList = commentList + "Nuevo comentario"
            }) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_add_24), contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovieCardDetail(movie)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = R.string.Comentarios), style = MaterialTheme.typography.headlineSmall)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(commentList) { comment ->
                    CommentCard(comment)
                }
            }
        }
    }
}

@Composable
fun CommentCard(comment: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = comment,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailFavScreenPreview() {
    DetailFavScreenContent(
        movie = DataSource.movieList().first(),
        comments = listOf("Genial película!", "Me encantó la trama.")
    )
}