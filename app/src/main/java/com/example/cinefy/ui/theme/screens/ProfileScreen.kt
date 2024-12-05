package com.example.cinefy.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinefy.R

class ProfileScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreenContent(
                userName = stringResource(R.string.nameUser),
                userEmail = stringResource(R.string.emailUser)
            )
        }
    }
}

@Composable
fun ProfileScreenContent(userName: String, userEmail: String, modifier: Modifier =Modifier) {
    var loggedIn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       Image(painter = painterResource(R.drawable.cinefylogo_copy), contentDescription = null, modifier.height(150.dp).width(150.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = userName, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = userEmail, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { loggedIn = !loggedIn }) {
            Text(text = if (loggedIn) stringResource(R.string.Logout) else stringResource(R.string.Login))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(userName = stringResource(R.string.nameUser), userEmail = stringResource(R.string.emailUser))
}
