package com.example.cinefy.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailItemScreen(
    movieViewModel: MovieViewModel = viewModel(),
    movie: Movie,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas

    Column(
        modifier = Modifier
            .padding(16.dp) // Ajusté el padding para mejorar el espaciado
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrar contenido en el eje horizontal
        verticalArrangement = Arrangement.Top // Ajusté la disposición para que se vea más equilibrado
    ) {
        // Header con el nombre de la película
        MedHeaderCompDetail(title = stringResource(R.string.Detail_Item), navController)

        // Dependiendo del tamaño de la pantalla, se adapta la vista
        if (isExpanded) {
            MovieCardDetailWithFavButton(movie)
        } else {
            MovieCardDetailWithFavButton(movie)
        }
    }
}

@Composable
fun MovieCardDetailWithFavButton(movie: Movie) {
    var isFavorite by remember { mutableStateOf(false) } // Variable para manejar el estado de favorito

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            MovieCardDetail(movie) // Componente de detalle de la película
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
                        text = if (isFavorite) stringResource(R.string.AddConfirm) else stringResource(R.string.AddFav)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun HeroListScreenPreview3() {
//    // Ejemplo de película con datos ficticios para la vista previa
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
//    )
//    DetailItemScreen(movie = exampleMovie, navController = NavController())
}

val LocalExtendedColorScheme3 = staticCompositionLocalOf {
    extendedLight //tomar cualquiera de los creados como referencia.
}

@Composable
fun MedHeaderComp3(title: String) {
    val extendedColorScheme = LocalExtendedColorScheme3.current
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
