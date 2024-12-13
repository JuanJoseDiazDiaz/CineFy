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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinefy.R
import com.example.cinefy.model.DataSource
import com.example.cinefy.model.Movie


@Composable
fun MovieCard(Movie: Movie) {
    Row {
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium,
            ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ImageComp(
                        drawable = DataSource.getDrawableIdName(Movie.image),
                        contentScale = ContentScale.Fit,
                    )
                    Box {
                        Row {
                            IconButton(onClick = {
                            }, Modifier.size(48.dp)) {
                                Icon(
                                    imageVector = Icons.TwoTone.Favorite,
                                    modifier = Modifier.size(40.dp),
                                    contentDescription = stringResource(R.string.more_content_desc),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

