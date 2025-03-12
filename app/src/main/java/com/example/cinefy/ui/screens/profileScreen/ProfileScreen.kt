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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R
import com.example.cinefy.data.UserPreferencesManager
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
    userPreferences: UserPreferencesManager,  // Ahora lo pasamos como parámetro
    modifier: Modifier = Modifier
) {
    var nameUser by remember { mutableStateOf("") }
    var themePreference by remember { mutableStateOf("system") }

    val coroutineScope = rememberCoroutineScope()

    // Obtener el nombre de usuario almacenado
    LaunchedEffect(Unit) {
        userPreferences.usernameFlow.collect { savedUsername ->
            savedUsername?.let { nameUser = it }
        }
    }

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

        // Guardar el nombre de usuario en DataStore
        Button(
            onClick = {
                coroutineScope.launch {
                    userPreferences.saveUsername(nameUser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Nombre")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Selector de tema
        Text(text = "Seleccionar tema:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                coroutineScope.launch { userPreferences.saveTheme("light") }
            }) { Text("Claro") }

            Button(onClick = {
                coroutineScope.launch { userPreferences.saveTheme("dark") }
            }) { Text("Oscuro") }

            Button(onClick = {
                coroutineScope.launch { userPreferences.saveTheme("system") }
            }) { Text("Sistema") }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun ProfileScreenPreview() {
//    ProfileScreenContent(
//
//    )
}
