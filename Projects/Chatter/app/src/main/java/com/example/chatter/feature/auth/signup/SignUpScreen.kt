package com.example.chatter.feature.auth.signup

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
fun SignUpScreen(navHostController: NavHostController) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val state = signUpViewModel.state.collectAsState()
    LaunchedEffect(key1 = state.value) {
        when (state.value) {
            is SignUpState.Success -> {
                navHostController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.SignUpScreen) { inclusive = true }
                }
            }
            is SignUpState.Error -> {
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
                value = name,
                onValueChange = {name = it}
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
                    signUpViewModel.signUp(name, email, password)
                }
            ) {
                Text("Sign up")
            }
            TextButton(
                onClick = {
                    navHostController.navigate(Routes.LoginScreen)
                }
            ) {
                Text("Already have an account?")
            }
        }
    }
}