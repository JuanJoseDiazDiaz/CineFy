package com.example.cinefy.ui.screens.profileScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.data.UserPreferencesManager
import com.example.cinefy.datamodel.Comment
import com.example.cinefy.datamodel.SingIn
import com.example.cinefy.ui.movie.MovieViewModel
import kotlinx.coroutines.launch

class ProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ProfileScreenContent(
////                userName = stringResource(R.string.nameUser),
////                userEmail = stringResource(R.string.emailUser)
//            )
        }
    }
}

@Composable
fun ProfileScreenContent(
    movieViewModel: MovieViewModel,
    userPreferences: UserPreferencesManager,
    modifier: Modifier = Modifier
) {
    val uiStateProfile by movieViewModel.uiStateProfile.collectAsState()
    var nameUser by remember { mutableStateOf(uiStateProfile.nameUser) }
    var passwordUser by remember { mutableStateOf(uiStateProfile.passwordUser) }
    var isRegistered by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Obtener el nombre de usuario almacenado
    LaunchedEffect(Unit) {
        userPreferences.usernameFlow.collect { savedUsername ->
            savedUsername?.let { nameUser = it }
        }

        // Verificar si el usuario ya está en la base de datos
        coroutineScope.launch {
            isRegistered = movieViewModel.isUserRegistered(nameUser)
        }
    }

    if (isRegistered) {
        RegisteredUserScreen(nameUser)
    } else {
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

            TextField(
                value = nameUser,
                onValueChange = { nameUser = it },
                label = { Text(text = "Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = passwordUser,
                onValueChange = { passwordUser = it },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            errorMessage?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(8.dp))
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val userExists = movieViewModel.isUserRegistered(nameUser)
                        val passwordExists = movieViewModel.isPasswordRegistered(passwordUser)

                        if (userExists) {
                            errorMessage = "El nombre de usuario ya está en uso."
                        } else if (passwordExists) {
                            errorMessage = "La contraseña ya está en uso. Elige otra."
                        } else {
                            userPreferences.saveUsername(nameUser)
                            userPreferences.savePassword(passwordUser)

                            val newUser = SingIn(userName = nameUser, password = passwordUser)
                            movieViewModel.insertarUsuario(newUser)

                            isRegistered = true
                            errorMessage = null // Limpiar mensaje de error
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar Usuario")
            }
        }
    }
}


@Composable
fun RegisteredUserScreen(nameUser: String) {
    Column(
        modifier = Modifier
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

        Text(text = "Bienvenido, $nameUser", color = Color.White)
    }
}


