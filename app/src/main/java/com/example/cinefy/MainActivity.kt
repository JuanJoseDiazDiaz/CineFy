package com.example.cinefy

import FavListScreenContent
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.data.DataCinefy
import com.example.cinefy.data.DataCinefy.findMovieByTitle
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.datamodel.MovieEntity
import com.example.cinefy.repository.MovieRepository
//import com.example.cinefy.datamodel.Movie
import com.example.cinefy.ui.screens.AboutUsScreen
import com.example.cinefy.ui.screens.ContactarCreadorIntent

import com.example.cinefy.ui.screens.profileScreen.ProfileScreenContent
import com.example.cinefy.ui.componets.BottomNavigationBar
import com.example.cinefy.ui.movie.MovieViewModel
import com.example.cinefy.ui.screens.DetailFavScreen
import com.example.cinefy.ui.screens.DetailItemScreen
import com.example.cinefy.ui.screens.favoritelist.FavListScrenViewModel
import com.example.cinefy.ui.screens.movieElementList.ElementListScreen
import com.example.cinefy.utils.getWindowSizeClass
import com.example.cinefy.ui.theme.CinefyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
//Datastore. Configuración básica de la app.
val Context.dataStore by preferencesDataStore(name = UserPreferencesManager.SETTINGS_FILE)
class MainActivity : ComponentActivity() {
    lateinit var userPreferencesRepository: UserPreferencesManager
    lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var favListScrenViewModel: FavListScrenViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inicialización del MovieViewModel usando la fábrica de la aplicación
        val app = application as MovieReleaseApplication
        movieViewModel = ViewModelProvider(this, app.viewModelFactory).get(MovieViewModel::class.java)

        // Pasar el movieViewModel al ViewModelFactory de FavListScrenViewModel
        val favFactory = FavListScrenViewModel.Factory(application = app, movieViewModel = movieViewModel)

        // Inicialización del FavListScrenViewModel
        favListScrenViewModel = ViewModelProvider(this, favFactory).get(FavListScrenViewModel::class.java)
        //Creación de la instancia del repositorio de preferencias de usuario
        userPreferencesRepository = UserPreferencesManager(dataStore)

        val userPreferences = UserPreferencesManager(dataStore)

        // Recuperar y aplicar el tema guardado
        lifecycleScope.launch {
            userPreferences.themeFlow.collectLatest { theme ->
                when (theme) {
                    "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
        val exampleMovie = MovieEntity(
            rank = 32,
            title = "OppenHeimer",
            description = "The story of American scientist, J. Robert Oppenheimer, " +
                    "and his role in the development of the atomic bomb.",
            imageUrl = "openheimerposter",
            bigImageUrl = "openheimerposter",
            genres = listOf("Historia, Accion"),
            thumbnailUrl = "openheimerposter",
            rating = 8.6f,
            id = "top32",
            year = 2023,
            imdbId = "tt15398776",
            imdbLink = "https://www.imdb.com/title/tt15398776"

        )
        setContent {
            val movies = DataCinefy.movieList()
            val windowSize = getWindowSizeClass(LocalContext.current as Activity)
            val navController = rememberNavController()
            val currentRoute by navController.currentBackStackEntryFlow.map { it.destination.route }
                .collectAsState(initial = null)

            CinefyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
//                        if (windowSize == WindowWidthSizeClass.Compact && currentRoute?.contains("details") == false)
                            BottomNavigationBar(navController, currentRoute)
                    }) { innerPadding ->
                    NavHost(navController = navController, startDestination = "movie_list") {
                        composable("movie_list") {
                            ElementListScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                            )
                        }
                        composable("lista_Fav") {
                            FavListScreenContent(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("pag_Profile") {
                            ProfileScreenContent(
                                movieViewModel,
                                userPreferences = userPreferences,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable("aboutUs") {
                            AboutUsScreen(
                                modifier = Modifier.padding(innerPadding),
                                onClickSendData = { name, tematica, descripcion, version ->
                                    val intent = ContactarCreadorIntent(name, tematica, descripcion, version)
                                    startActivity(
                                        Intent.createChooser(
                                            intent,
                                            "Enviar datos a través de..."
                                        )
                                    )
                                }
                            )
                        }
                        composable("details/{movie_title}") { backStackEntry ->
                            val movieTitle = backStackEntry.arguments?.getString("movie_title")
                            val movie = findMovieByTitle(movies, movieTitle ?: "") ?: exampleMovie
                            DetailItemScreen(
                                movieTitle = movieTitle,
                                modifier = Modifier.padding(innerPadding),
                                comentarioViewModel = movieViewModel,
                                navController = navController
                            )
                        }
                        composable("details_fav/{movie_title}") { backStackEntry ->
                            val movieTitle = backStackEntry.arguments?.getString("movie_title")
                            val movie = findMovieByTitle(movies, movieTitle ?: "") ?: exampleMovie
                            DetailFavScreen(
                                movieTitle = movieTitle,
                                navController = navController,
                                comentarioViewModel = movieViewModel,
                                modifier = Modifier.padding(innerPadding),
                            )
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
