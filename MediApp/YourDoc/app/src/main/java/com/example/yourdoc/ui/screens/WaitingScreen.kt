package com.example.yourdoc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.yourdoc.ui.screens.navigation.Routes
import com.example.yourdoc.viewmodel.MyViewModel

@Composable
fun WaitingScreen(userID: String, viewModel: MyViewModel, navController: NavController) {
    Text("This is Waiting Screen")

    LaunchedEffect(key1 = Unit) {
        viewModel.getUserById(userID)
    }

    val userByIdState = viewModel.userByIdState.collectAsState()

    when {
        userByIdState.value.isLoading -> {
            Text("Loading")
        }

        userByIdState.value.error != null -> {
            Text("Error getting user")
        }

        userByIdState.value.success != null -> {
            if (userByIdState.value.success!!.isApproved == 1) {
                navController.navigate(Routes.HomeScreenRoute)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Wait for approval")

                    Button(
                        onClick = {
                            viewModel.getUserById(userID)
                        }
                    ) {
                        Text("Refresh")
                    }
                }

            }
        }
    }

}