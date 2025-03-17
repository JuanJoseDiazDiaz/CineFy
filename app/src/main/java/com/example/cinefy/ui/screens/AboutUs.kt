package com.example.cinefy.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinefy.R
import com.example.cinefy.ui.componets.StandardButtonImage
import com.example.cinefy.ui.screens.movieElementList.MedHeaderComp

/**
 * Implementacion por parameteros el viewModel para que se conecte entre si
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
//    movieViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory()),
    modifier: Modifier = Modifier,
    nameApp: String = "Cinefy",
    tematica: String = "Cinematográfica",
    descripcion: String = "Cinefy es una aplicación de películas que ofrece recomendaciones personalizadas basadas en tus gustos y te permite explorar colecciones temáticas, desde clásicos hasta estrenos recientes. Los usuarios pueden calificar, hacer listas propias, ver dónde están disponibles las películas en streaming y unirse a una comunidad de cinéfilos para compartir reseñas y descubrir nuevas joyas del cine. ¡Todo lo que necesitas para tu próxima maratón de películas está en Cinefy!",
    version: Float = 12.0f,
    onClickSendData: (nameApp: String, tematica: String, descripcion: String, version: Float) -> Unit

) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.title_AboutUs))
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black, titleContentColor = Color.White
            )
        )
    }) { innerPadding ->
        PresentationAboutUs(
            name = nameApp,
            tematica = tematica,
            descripcion = descripcion,
            version = version,
            onClickSendData = { onClickSendData(nameApp, tematica, descripcion, version) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun PresentationAboutUs(
    name: String,
    tematica: String,
    descripcion: String,
    version: Float,
    onClickSendData: () -> Unit,
    modifier: Modifier = Modifier

) {
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600
    if (isExpanded) {
        MedHeaderComp(stringResource(R.string.title_AboutUs))
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = colorResource(id = R.color.Naranja_Pastel)),
                horizontalArrangement = Arrangement.Center

            ) {
                Text(text = name, style = TextStyle(color = Color.White, fontSize = 27.sp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.Tematica) + tematica,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.Descripcion) + descripcion,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Version) + version,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                StandardButtonImage(icon = painterResource(id = R.drawable.icon_share)) {
                    onClickSendData()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ShowImage()
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = colorResource(id = R.color.Naranja_Pastel)),
                horizontalArrangement = Arrangement.Center

            ) {
                Text(text = name, style = TextStyle(color = Color.White, fontSize = 27.sp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.Tematica) + tematica,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.Descripcion) + descripcion,
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Version) + version,
                    style = TextStyle(fontSize = 22.sp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                StandardButtonImage(icon = painterResource(id = R.drawable.icon_share)) {
                    onClickSendData()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                ShowImage()
            }
        }
    }

}

@Composable
private fun ShowImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.cinefylogo) // Carga la imagen a mostrar
    // Imagen de tamaño fijo de 250dp de ancho
    Image(
        painter = image, contentDescription = null, modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    )
}

fun ContactarCreadorIntent(
    nameApp: String,
    tematica: String,
    descripcion: String,
    version: Float
): Intent {
    val ASUNTO = "EXTRA_SUBJECT"
    val CUERPO = "EXTRA_TEXT"
    val asunto = "La aplicación llamada es: $nameApp, tengo dudas o inconvenientes con ella"
    val tematicaInfo = "La temática es la siguiente: $tematica"
    val descripcionInfo = "Esta es la descripción de la app: $descripcion"
    val versionInfo = "La versión es: $version"

    return Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(ASUNTO, asunto)
        putExtra(CUERPO, "$tematicaInfo\n$descripcionInfo\n$versionInfo")
        type = "text/plain"
    }
}