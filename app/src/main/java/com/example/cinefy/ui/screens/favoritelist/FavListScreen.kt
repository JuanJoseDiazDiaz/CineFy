import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.R
import com.example.cinefy.datamodel.Movie
import com.example.cinefy.datamodel.toMovie
import com.example.cinefy.datamodel.toMovieEntity
import com.example.cinefy.ui.screens.movieElementList.MovieViewModel
import com.example.cinefy.ui.screens.favoritelist.FavListScrenViewModel
import com.example.cinefy.ui.screens.movieElementList.MedHeaderComp

class FavListScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            FavListScreenContent()
        }
    }
}
/**
 * Implementacion por parameteros el viewModel para que se conecte entre si
 * */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavListScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val app = LocalContext.current.applicationContext as MovieReleaseApplication

    // Inicializar MovieViewModel
    val movieViewModel: MovieViewModel = viewModel(
        factory = app.viewModelFactory
    )

    // Inicializar FavListScrenViewModel pasando MovieViewModel como parámetro
    val favFactory = FavListScrenViewModel.Factory(
        application = app,
        movieViewModel = movieViewModel,
        moviUiState = movieViewModel.uiState.value,
    )
    val favListViewModel: FavListScrenViewModel = viewModel(factory = favFactory)
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600
    val favoriteMovies by favListViewModel.uiState.collectAsState() // Lista de favoritos desde el ViewModel

    if (isExpanded) {
        Column(modifier = modifier.padding(10.dp)) {
            MedHeaderComp(title = stringResource(R.string.DetailFavoritos))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 50.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                items(favoriteMovies.favorites) { movie ->
                    MovieCardWithRemoveButton(
                        movie.toMovie(),
                        onClickNavegator = {
                            navController.navigate("details_fav/${movie.title}")
                        },
                        onRemove = { removedMovie ->
                           favListViewModel.borrarFavorito(movie)
                        }
                    )
                }
            }
        }
    } else {
        Column(modifier = modifier.padding(10.dp)) {
            MedHeaderComp(title = stringResource(R.string.DetailFavoritos))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 50.dp,
                    end = 12.dp,
                    bottom = 16.dp
                )
            ) {
                items(favoriteMovies.favorites) { movie ->
                    MovieCardWithRemoveButton(
                        movie.toMovie(),
                        onClickNavegator = {
                            navController.navigate("details_fav/${movie.title}")
                        },
                        onRemove = { removedMovie ->
                            // Aquí utilizamos el metodo borrarFavorito
                            favListViewModel.borrarFavorito(removedMovie.toMovieEntity())
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MovieCardWithRemoveButton(movie: Movie, onRemove: (Movie) -> Unit, onClickNavegator: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieCardDetail(movie, onClickNavegator)
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
            text = { Text("¿Estás seguro de que deseas eliminar la pelicula ${movie.title} de tus favoritos?") },
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
//    FavListScreenContent()
}

@Composable
fun MovieCardDetail(movie: Movie, onClick: () -> Unit) {
    Row {
        Card(
            modifier = Modifier
                .padding(8.dp).clickable { onClick() },
            shape = MaterialTheme.shapes.medium,
        ) {
            AsyncImage(
                model = movie.imageUrl, // Asume que Movie.imageUrl es una URL
                contentDescription = "Movie Poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp) // Ajusta el tamaño de la imagen según lo necesites
            )
        }
    }
}
