package com.example.yourdoc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.yourdoc.ui.screens.navigation.Routes
import com.example.yourdoc.viewmodel.MyViewModel

@Composable
fun LoginScreen(viewModel: MyViewModel, navController: NavHostController) {
    val state = viewModel.loginUserState.collectAsState()
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    when {
        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {

            Text(state.value.error.toString())
        }

        state.value.success != null -> {
//            Column(modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(state.value.success!!.message)
//                Text(state.value.success!!.status.toString())
//            }
            if (state.value.success!!.status == 200) {

                navController.navigate(Routes.WaitingRoute(state.value.success!!.message))
            }
        }


    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") }
        )

        Button(onClick = {
            viewModel.loginUser(email.value, password.value)
        }) {

            Text("Log In")
        }
    }
}