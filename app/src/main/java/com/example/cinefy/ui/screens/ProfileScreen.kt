package com.example.cinefy.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinefy.R
import com.example.cinefy.ui.movie.MovieViewModel

class ProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreenContent(
//                userName = stringResource(R.string.nameUser),
//                userEmail = stringResource(R.string.emailUser)
            )
        }
    }
}

@Composable
fun ProfileScreenContent(movieViewModel: MovieViewModel = viewModel(), modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isExpanded = configuration.screenWidthDp > 600
    var loggedIn by remember { mutableStateOf(false) }
    // Conecta con entre sí con el dataStore
    val movieUiState by movieViewModel.uiState.collectAsState()

    // Estado para manejar la edición de los campos
    var nameUser by remember { mutableStateOf(movieUiState.nameUser) }
    var passwordUser by remember { mutableStateOf(movieUiState.passwordUser) }
    var emailUser by remember { mutableStateOf(movieUiState.emailUser) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.cinefylogo_copy),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // TextField para el nombre de usuario
        TextField(
            value = nameUser,
            onValueChange = { nameUser = it },
            label = { Text(text = "Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // TextField para la contraseña
        TextField(
            value = passwordUser,
            onValueChange = { passwordUser = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),  // Esto oculta la contraseña
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // TextField para el correo electrónico
        TextField(
            value = emailUser,
            onValueChange = { emailUser = it },
            label = { Text(text = "Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { loggedIn = !loggedIn },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (loggedIn) Color.Red else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (loggedIn) stringResource(R.string.Logout) else stringResource(R.string.Login)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(

    )
}
