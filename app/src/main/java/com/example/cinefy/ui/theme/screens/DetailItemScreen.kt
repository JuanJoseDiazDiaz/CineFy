import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.componets.ImageComp
import com.example.cinefy.ui.theme.componets.MovieCardDetail
import com.example.cinefy.ui.theme.componets.StandardTextComp
import com.example.compose.extendedLight

@Composable
fun DetailItemScreen(movies: MutableList<Movie>, modifier: Modifier = Modifier) {
    // Detectar el tamaño de la pantalla
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas

    Column(modifier = modifier.fillMaxSize()) {
        // Uso de MedHeaderComp para la cabecera
        MedHeaderComp3(title = stringResource(R.string.Detail_Item))
        if (isExpanded) {
            // Pantalla expandida
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(movies) { movie ->
                    MovieCardDetailWithFavButton(movie)
                }
            }
        } else {
            // Pantalla compacta
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(movies) { movie ->
                    MovieCardDetailWithFavButton(movie)
                }
            }
        }
    }
}

@Composable
fun MovieCardDetailWithFavButton(movie: Movie) {
    var isFavorite by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieCardDetail(movie)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
}


@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview3() {
    DetailItemScreen(DataSource.movieList())
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

