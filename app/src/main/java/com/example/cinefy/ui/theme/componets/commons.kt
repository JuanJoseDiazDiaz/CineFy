package com.example.cinefy.ui.theme.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
