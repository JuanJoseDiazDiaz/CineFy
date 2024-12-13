package com.example.cinefy

import DetailFavScreen
import DetailItemScreen
import FavListScreenContent
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.componets.BottomNavigationBar
import com.example.cinefy.ui.theme.screens.AboutUsScreen
import com.example.cinefy.ui.theme.screens.ContactarCreadorIntent
import com.example.cinefy.ui.theme.screens.ElemtListScreen
import com.example.cinefy.ui.theme.screens.ProfileScreenContent
import com.example.cinefy.ui.theme.utils.getWindowSizeClass
import com.example.compose.CinefyTheme
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val movie = DataSource.getListXtimes(4)
            val windowSize = getWindowSizeClass(LocalContext.current as Activity)
            val navController = rememberNavController()
            val currentRoute by navController.currentBackStackEntryFlow.map { it.destination.route }
                .collectAsState(initial = null)

            CinefyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (windowSize == WindowWidthSizeClass.Compact && currentRoute?.contains("details") == false)
                            BottomNavigationBar(navController, currentRoute)
                    }) { innerPadding ->
                    val exampleMovie = Movie(
                        rank = 32,
                        title = "OppenHeimer",
                        descripcion = "The story of American scientist, J. Robert Oppenheimer, " +
                                "and his role in the development of the atomic bomb.",
                        image = "openheimerposter",
                        bigimage = "openheimerposter",
                        genre = "History",
                        thumbanil = "openheimerposter",
                        ranting = 8.6f,
                        id = "top32",
                        yearEstreno = 2023,
                        imdbid = "tt15398776",
                        imdbid_link = "https://www.imdb.com/title/tt15398776"
                    )
                    // todo -> Navegación
                    NavHost(navController = navController, startDestination = "lista_Fav") {
                        composable("movie_list") {

                        }
                        composable("lista_Fav") {
                            FavListScreenContent(
                                favoriteMovies = DataSource.movieList(),
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("pag_Profile") {
                            ProfileScreenContent(
                                userName = stringResource(R.string.nameUser),
                                userEmail = stringResource(R.string.emailUser),
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("aboutUs") {
                            AboutUsScreen(
                                modifier = Modifier.padding(innerPadding), // Pasamos el innerPadding al AboutUsScreen
                                onClickSendData = { name, tematica, descripcion, version ->
                                    val intent =
                                        ContactarCreadorIntent(name, tematica, descripcion, version)
                                    startActivity(
                                        Intent.createChooser(
                                            intent,
                                            "Enviar datos a través de..."
                                        )
                                    )
                                }

                            )
                        }
                        composable("details/{movie_title}") {
                            DetailItemScreen(movies = exampleMovie,  modifier = Modifier.padding(innerPadding))
                        }
                        composable("details_fav/{movie_title}") {
                            DetailFavScreen(movies = exampleMovie, modifier = Modifier.padding(innerPadding))
                        }
                    }
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
    CinefyTheme {
        Greeting("Android")
    }
}