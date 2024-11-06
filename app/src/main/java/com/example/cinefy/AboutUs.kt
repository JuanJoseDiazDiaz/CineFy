package com.example.cinefy

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinefy.ui.theme.CinefyTheme

class AboutUs : ComponentActivity() {
    // todo -> Creacion de variables necesarias para el metodo PresentationAboutUs
    private var nameApp: String = "Cinefy"
    private var tematica: String = "Cinematográfica"
    private var descripcion: String =
        "Cinefy es una aplicación de películas que ofrece recomendaciones personalizadas basadas en " +
                "tus gustos y te permite explorar colecciones temáticas, desde clásicos hasta estrenos recientes. " +
                "Los usuarios pueden calificar, hacer listas propias, ver dónde están disponibles las películas en streaming y" +
                " unirse a una comunidad de cinéfilos para compartir reseñas y descubrir nuevas joyas del cine. " +
                "¡Todo lo que necesitas para tu próxima maratón de películas está en Cinefy!"
    private var version: Float = 12.0f


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinefyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.title_AboutUs))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White
                            )
                        )
                    }
                ) { innerPadding ->
                    PresentationAboutUs(
                        nameApp,
                        tematica,
                        descripcion,
                        version,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var nameApp: String = "Cinefy"
    var tematica: String = "Cinematográfica"
    var descripcion: String =
        "Cinefy es una aplicación de películas que ofrece recomendaciones personalizadas basadas en " +
                "tus gustos y te permite explorar colecciones temáticas, desde clásicos hasta estrenos recientes. " +
                "Los usuarios pueden calificar, hacer listas propias, ver dónde están disponibles las películas en streaming y" +
                " unirse a una comunidad de cinéfilos para compartir reseñas y descubrir nuevas joyas del cine. " +
                "¡Todo lo que necesitas para tu próxima maratón de películas está en Cinefy!"
    var version: Float = 12.0f
    CinefyTheme {
        PresentationAboutUs(nameApp, tematica, descripcion, version)
    }
}

@Composable
private fun PresentationAboutUs(
    name: String,
    tematica: String,
    descripcion: String,
    version: Float,
    modifier: Modifier = Modifier
) {
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
            Text(text = stringResource(id = R.string.Descripcion) + descripcion)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
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
            ShowImage()
        }
    }
}

@Composable
private fun ShowImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.cinefylogo) // Carga la imagen a mostrar
    // Imagen de tamaño fijo de 250dp de ancho
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier.width(250.dp).height(400.dp)
    )

}


