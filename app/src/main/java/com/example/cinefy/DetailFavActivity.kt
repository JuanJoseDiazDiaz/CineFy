package com.example.cinefy

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
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinefy.model.DataSource
import com.example.cinefy.ui.theme.screens.DetailFavScreenContent
import com.example.cinefy.ui.theme.screens.ui.theme.CinefyTheme

class DetailFavActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinefyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailFavScreenContent(DataSource.movieList().first(), listOf("Genial película!", "Me encantó la trama."), Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    CinefyTheme {
        DetailFavScreenContent(DataSource.movieList().first(), listOf("Genial película!", "Me encantó la trama."))
    }
}