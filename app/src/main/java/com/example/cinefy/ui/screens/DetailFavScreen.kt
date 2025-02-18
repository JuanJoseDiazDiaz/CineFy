import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.componets.MovieCardDetail
import com.example.cinefy.ui.movie.MovieViewModel
import com.example.cinefy.ui.screens.MedHeaderCompDetail
import com.example.cinefy.ui.theme.extendedLight
/**
 * Implementacion por parameteros el viewModel para que se conecte entre si
 * */
@Composable
fun DetailFavScreen(movieViewModel: MovieViewModel = viewModel(), movies: Movie, navController: NavController, modifier: Modifier = Modifier) {
    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas

    Column(
        modifier = Modifier.padding(60.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrar contenido en el eje horizontal
        verticalArrangement = Arrangement.Center // Centrar contenido en el eje vertical
    ) {
        Row {
            MedHeaderCompDetail(title = stringResource(R.string.Detail_Item), navController)
        }
        if (isExpanded) {
            MovieCardDetailWithFavButton2(movies)
        } else {
            MovieCardDetailWithFavButton2(movies)
        }
    }
}


@Composable
fun MovieCardDetailWithFavButton2(movie: Movie) {
    var isFavorite by remember { mutableStateOf(false) }
    // Toda la pantalla es una LazyColumn
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mostrar la tarjeta de detalles de la película
        item {
            MovieCardDetail(movie)
        }

        // Espacio entre la tarjeta y el siguiente contenido
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Botón de favoritos
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    isFavorite = !isFavorite
                }) {
                    Text(
                        text = if (isFavorite) stringResource(R.string.AddConfirm) else stringResource(
                            R.string.AddFav
                        )
                    )
                }
            }
        }

        // Espacio entre el texto y la lista de elementos
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(stringResource(R.string.Comentarios), style = MaterialTheme.typography.headlineMedium,)
                Spacer(modifier = Modifier.width(5.dp))
                FloatingActionButton(
                    onClick = {
                        // Acción al hacer clic en el FAB
                    },
                    modifier = Modifier.size(48.dp), // Ajusta el tamaño del FAB
                    containerColor = MaterialTheme.colorScheme.primary // Color del botón
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle, // Ícono del FAB
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary // Color del ícono
                    )
                }
            }
        }

        // LazyColumn interna para mostrar una lista de elementos (por ejemplo, comentarios)
        items(1) { index ->
            // Aquí puedes reemplazar este contenido con tus elementos reales, como comentarios, etc.
            Row {
                Icon(
                    imageVector = Icons.TwoTone.AccountCircle,
                    modifier = Modifier.size(48.dp),
                    contentDescription = stringResource(R.string.more_content_desc),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Magnífica dirección y actuación en una historia conmovedora.",
                    modifier = Modifier.padding(8.dp)
                )

            }
        }
        items(1) { index ->
            // Aquí puedes reemplazar este contenido con tus elementos reales, como comentarios, etc.
            Row {
                Icon(
                    imageVector = Icons.TwoTone.AccountCircle,
                    modifier = Modifier.size(48.dp),
                    contentDescription = stringResource(R.string.more_content_desc),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Una película impactante y profunda sobre la ciencia y la moral.",
                    modifier = Modifier.padding(8.dp)
                )

            }

        }
        items(1) { index ->
            // Aquí puedes reemplazar este contenido con tus elementos reales, como comentarios, etc.

            Row {
                Icon(
                    imageVector = Icons.TwoTone.AccountCircle,
                    modifier = Modifier.size(48.dp),
                    contentDescription = stringResource(R.string.more_content_desc),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Un retrato fascinante de Oppenheimer y su dilema ético.",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 400)
@Composable
fun HeroListScreenPreview4() {
//    // Crea una instancia de Movie con datos de ejemplo.
//    val exampleMovie = Movie(
//        rank = 32,
//        title = "OppenHeimer",
//        descripcion = "The story of American scientist, J. Robert Oppenheimer, " +
//                "and his role in the development of the atomic bomb.",
//        image = "openheimerposter",
//        bigimage = "openheimerposter",
//        genre = "History",
//        thumbanil = "openheimerposter",
//        ranting = 8.6f,
//        id = "top32",
//        yearEstreno = 2023,
//        imdbid = "tt15398776",
//        imdbid_link = "https://www.imdb.com/title/tt15398776"
//
//
//    )
//    DetailItemScreen(movies = exampleMovie)
}


val LocalExtendedColorScheme4 = staticCompositionLocalOf {
    extendedLight //tomar cualquiera de los creados como referencia.
}


