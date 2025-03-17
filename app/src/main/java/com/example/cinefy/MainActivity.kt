package com.example.cinefy

import FavListScreenContent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinefy.MovieReleaseApplication.MovieReleaseApplication
import com.example.cinefy.data.DataCinefy
import com.example.cinefy.data.DataCinefy.findMovieByTitle
//import com.example.cinefy.datamodel.Movie
import com.example.cinefy.ui.screens.AboutUsScreen
import com.example.cinefy.ui.screens.ContactarCreadorIntent

import com.example.cinefy.ui.screens.profileScreen.ProfileScreenContent
import com.example.cinefy.ui.componets.BottomNavigationBar
import com.example.cinefy.ui.screens.movieElementList.MovieViewModel
import com.example.cinefy.ui.screens.DetailFavScreen
import com.example.cinefy.ui.screens.DetailItemScreen
import com.example.cinefy.ui.screens.favoritelist.FavListScrenViewModel
import com.example.cinefy.ui.screens.movieElementList.ElementListScreen
import com.example.cinefy.ui.screens.profileScreen.ModoVisualizacionPantalla
import com.example.cinefy.ui.screens.profileScreen.ProfileViewModel
import com.example.cinefy.ui.theme.CinefyTheme
import kotlinx.coroutines.flow.map

//val Context.dataStore by preferencesDataStore(name = UserPreferencesManager.SETTINGS_FILE)

class MainActivity : ComponentActivity() {
    // Repositorios y ViewModels
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var favListScrenViewModel: FavListScrenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Recuperar la instancia de MovieReleaseApplication
        val app = application as MovieReleaseApplication

        // Recuperar el ViewModel de Movie
        movieViewModel = ViewModelProvider(this, app.viewModelFactory)
            .get(MovieViewModel::class.java)

        // Configurar el ViewModel de la lista de favoritos
        val favFactory = FavListScrenViewModel.Factory(
            application = app,
            movieViewModel = movieViewModel,
            moviUiState = movieViewModel.uiState.value
        )
        favListScrenViewModel = ViewModelProvider(this, favFactory)
            .get(FavListScrenViewModel::class.java)


        // Usar la instancia global de UserPreferencesManager desde MovieReleaseApplication
        val userPreferences = app.userPreferencesRepository


        // Contenido de la interfaz de usuario
        setContent {
            val movies = DataCinefy.movieList()
            val navController = rememberNavController()
            val currentRoute by navController.currentBackStackEntryFlow.map { it.destination.route }
                .collectAsState(initial = null)

            val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
            val profileUiState by movieViewModel.uiStateProfile.collectAsState()
            val isDarkTheme = when( profileUiState.modoDeVisualizacionPantalla){
                ModoVisualizacionPantalla.CLARO -> false
                ModoVisualizacionPantalla.OSCURO -> true
                ModoVisualizacionPantalla.SISTEMA -> isSystemInDarkTheme()
            }

            CinefyTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController, currentRoute)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "movie_list"
                    ) {

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
                                profileViewModel = profileViewModel,
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
                            val movie = findMovieByTitle(movies, movieTitle ?: "") ?: DataCinefy.exampleMovie
                            DetailItemScreen(
                                movieTitle = movieTitle,
                                modifier = Modifier.padding(innerPadding),
                                comentarioViewModel = movieViewModel,
                                navController = navController
                            )
                        }
                        composable("details_fav/{movie_title}") { backStackEntry ->
                            val movieTitle = backStackEntry.arguments?.getString("movie_title")
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
