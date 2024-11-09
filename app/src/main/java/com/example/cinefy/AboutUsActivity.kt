package com.example.cinefy

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cinefy.ui.theme.CinefyTheme
import com.example.cinefy.ui.theme.componets.StandardButtonImage

class AboutUsActivity : ComponentActivity() {

    companion object {
        private const val ASUNTO = "EXTRA_SUBJECT"
        private const val CUERPO = "EXTRA_TEXT"
    }
    // todo -> Metodo ContactarCreador:
    /**
     * Este metodo realiza la función de enviar los datos obtenidos atraves de un intent,
     * de forma que cuando se habrá la aplicación que desees de envios salga un asunto concreto y un cuerpo concreto
     * */
    fun ContactarCreador(
        nameApp: String,
        tematica: String,
        descripcion: String,
        version: Float,
        modifier: Modifier = Modifier
    ) {
        val asunto = "La aplicación llamada es: ${nameApp}, tengo dudas o inconvenientes con ella"
        val tematica = "La tematica es la siguiente: ${tematica}"
        val descripcion = "Esta es la descripcion de la app: ${descripcion}"
        val version = "La versión es: ${version}"

        // Configura un Intent de tipo "SEND" para compartir texto plano
        val enviarDatos = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(ASUNTO, asunto) // Asunto del mensaje
            putExtra(CUERPO, tematica) // Cuerpo del mensaje
            putExtra(CUERPO, descripcion) // Cuerpo del mensaje
            putExtra(CUERPO, version) // Cuerpo del mensaje
            type = "text/plain"
        }
        // Inicia el Intent permitiendo al usuario elegir la aplicación para compartir los datos
        startActivity(Intent.createChooser(enviarDatos, "Enviar datos a través de..."))
    }

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
        val screenSplash = installSplashScreen()
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
                        { ContactarCreador(nameApp, tematica, descripcion, version) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        screenSplash.setKeepOnScreenCondition{false}
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Composable
//fun GreetingPreview(onClickSendData: () -> Unit) {
//    var nameApp: String = "Cinefy"
//    var tematica: String = "Cinematográfica"
//    var descripcion: String =
//        "Cinefy es una aplicación de películas que ofrece recomendaciones personalizadas basadas en " +
//                "tus gustos y te permite explorar colecciones temáticas, desde clásicos hasta estrenos recientes. " +
//                "Los usuarios pueden calificar, hacer listas propias, ver dónde están disponibles las películas en streaming y" +
//                " unirse a una comunidad de cinéfilos para compartir reseñas y descubrir nuevas joyas del cine. " +
//                "¡Todo lo que necesitas para tu próxima maratón de películas está en Cinefy!"
//    var version: Float = 12.0f
//
//    CinefyTheme {
//        PresentationAboutUs(nameApp, tematica, descripcion, version, onClickSendData)
//    }
//}

@Composable
private fun PresentationAboutUs(
    name: String,
    tematica: String,
    descripcion: String,
    version: Float,
    onClickSendData: () -> Unit,
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

@Composable
private fun ShowImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.cinefylogo) // Carga la imagen a mostrar
    // Imagen de tamaño fijo de 250dp de ancho
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
            .width(650.dp)
            .height(650.dp)
    )

}


