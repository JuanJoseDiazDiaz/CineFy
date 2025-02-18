package com.example.cinefy.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie
import com.example.cinefy.ui.screens.MedHeaderComp

@Composable
fun StandardButtonImage(icon : Painter, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        )
    }
}
@Composable
fun ImageComp(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    drawable: Int,
    contentDesc: String = "",
    height: Int = 0,
    width: Int = 0
) {
    val contentDescription =
       null
    if(height != 0 && width != 0) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            modifier
                .height(height.dp)
                .width(width.dp),
            contentScale = contentScale
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}
@Composable
fun StandardTextComp(text: String, modifier: Modifier = Modifier, style  : androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}


@Composable
fun ListCompactScreen(movies: MutableList<Movie>, navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(movies) { movie ->
               MovieCard(movie, onClick = {navController.navigateUp()})
            }
        }
    }
}

@Composable
fun ListMedExpScreen(movies: MutableList<Movie>, navController: NavController, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = "Pantalla media o grande")
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(movies) { movie ->
                MovieCard(movie, onClick = {navController.navigateUp()})
            }
        }
    }
}