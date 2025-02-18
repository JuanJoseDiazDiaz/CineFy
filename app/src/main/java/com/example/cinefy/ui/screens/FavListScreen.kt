import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinefy.R
import com.example.cinefy.data.DataCinefy
import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.componets.ImageComp
import com.example.cinefy.ui.movie.MovieViewModel
import com.example.cinefy.ui.screens.MedHeaderComp

class FavListScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FavListScreenContent(favoriteMovies = DataCinefy.movieList())
        }
    }
}
/**
 * Implementacion por parameteros el viewModel para que se conecte entre si
 * */
@Composable
fun FavListScreenContent(movieViewModel: MovieViewModel = viewModel(), favoriteMovies: List<Movie>, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600
    var favorites by remember { mutableStateOf(favoriteMovies) }
    if(isExpanded){
       Column (modifier = modifier.padding(10.dp)){
           MedHeaderComp(title = stringResource(R.string.DetailFavoritos))
           LazyVerticalGrid(
               columns = GridCells.Fixed(2),
               contentPadding = PaddingValues(
                   start = 12.dp,
                   top = 50.dp,
                   end = 12.dp,
                   bottom = 16.dp
               ),
               content = {
                   items(favorites) { movie ->
                       MovieCardWithRemoveButton(
                           movie,
                           onRemove = { removedMovie ->
                               favorites = favorites.filter { it != removedMovie }
                           })
                   }
               }
           )
       }
    }else{
        Column (modifier = modifier.padding(10.dp)){
            MedHeaderComp(title = stringResource(R.string.DetailFavoritos))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 50.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                content = {
                    items(favorites) { movie ->
                        MovieCardWithRemoveButton(
                            movie,
                            onRemove = { removedMovie ->
                                favorites = favorites.filter { it != removedMovie }
                            })
                    }
                }
            )
        }
    }

}

@Composable
fun MovieCardWithRemoveButton(movie: Movie, onRemove: (Movie) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieCardDetail(movie)
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = { showDialog = true }) {
            Icon(
                imageVector = Icons.TwoTone.Delete,
                modifier = Modifier.size(48.dp),
                contentDescription = stringResource(R.string.more_content_desc),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Confirmación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta película de tus favoritos?") },
            confirmButton = {
                TextButton(onClick = {
                    onRemove(movie)
                    showDialog = false
                }) {
                    Text("Sí, estoy seguro")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("No, cancelar")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FavListScreenPreview() {
    FavListScreenContent(favoriteMovies = DataCinefy.movieList())
}

@Composable
fun MovieCardDetail(movie: Movie) {
    Row {
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            ImageComp(drawable = DataCinefy.getDrawableIdName(movie.image))
        }
    }
}
