package com.example.chatter.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatter.Routes

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val signInViewModel: SignInViewModel = hiltViewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState = signInViewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when(uiState.value) {
            is SignInState.Success -> {
                navHostController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.LoginScreen) { inclusive = true }
                }
            }
            is SignInState.Error -> {
                // Handle error state, e.g., show a snackbar or dialog
            }
            else -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Image(
                imageVector = Icons.Default.Face,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White)
            )
            OutlinedTextField(
                value = email,
                onValueChange = {email = it}
            )
            OutlinedTextField(
                value = password,
                onValueChange = {password = it}
            )
            Button(
                onClick = {
                    signInViewModel.signIn(email, password)
                }
            ) {
                Text("Sign in")
            }

            TextButton(
                onClick = {
                    navHostController.navigate(Routes.SignUpScreen)
                }
            ) {
                Text("Create one")
            }
        }
    }
}
























