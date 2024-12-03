package com.example.cinefy.ui.theme.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.cinefy.R

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
fun ResponsiveScreen(content: @Composable (isExpanded: Boolean) -> Unit) {
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600 // Define el umbral para pantallas expandidas

    content(isExpanded)
}