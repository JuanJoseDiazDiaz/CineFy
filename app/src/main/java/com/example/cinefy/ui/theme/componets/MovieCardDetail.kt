package com.example.cinefy.ui.theme.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie

@Composable
fun MovieCardDetail(Movie: Movie) {
    var isTooltipVisible by remember { mutableStateOf(false) }
    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImageComp(drawable = DataSource.getDrawableIdName(Movie.image), height = 500, width = 500)
        }
    }
    StandardTextComp(
        text = "${Movie.title}",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(8.dp)
    )
    Box {
        Button(onClick = { isTooltipVisible = true }) {
            Text(stringResource(R.string.VerDetalles))
        }
        if (isTooltipVisible) {
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { isTooltipVisible = false }
            ) {
                Card(
                    modifier = Modifier
                        .padding(10.dp),
                    shape = MaterialTheme.shapes.medium,

                    ) {
                    Column {
                        StandardTextComp(
                            text = "Genero: ${Movie.genre}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Rank: ${Movie.rank}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Fecha de Estreno: ${Movie.yearEstreno}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        StandardTextComp(
                            text = "Descripcion: ${Movie.descripcion}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
    }
}