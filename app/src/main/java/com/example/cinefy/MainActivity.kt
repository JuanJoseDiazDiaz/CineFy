package com.example.cinefy

import DetailFavScreen
import DetailItemScreen
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie
import com.example.cinefy.ui.theme.utils.getWindowSizeClass
import com.example.compose.CinefyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val movie = DataSource.getListXtimes(4)
            val windowSize = getWindowSizeClass(LocalContext.current as Activity)
            CinefyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    DetailFavScreen(exampleMovie, modifier = Modifier.padding(innerPadding))
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