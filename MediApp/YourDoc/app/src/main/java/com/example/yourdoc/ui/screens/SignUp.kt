package com.example.yourdoc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.yourdoc.ui.screens.navigation.Routes
import com.example.yourdoc.viewmodel.MyViewModel

@Composable
fun SignUp(viewModel: MyViewModel, navController: NavHostController) {
    val name = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val pinCode = rememberSaveable { mutableStateOf("") }
    val address = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name.value,
            onValueChange = {name.value = it},
            label = { Text("Name") }
        )
        TextField(
            value = password.value,
            onValueChange = {password.value = it},
            label = { Text("Password") }
        )
        TextField(
            value = phoneNumber.value,
            onValueChange = {phoneNumber.value = it},
            label = { Text("Phone Number") }
        )

        TextField(
            value = pinCode.value,
            onValueChange = {pinCode.value = it},
            label = { Text("Pin Code") }
        )
        TextField(
            value = address.value,
            onValueChange = {address.value = it},
            label = { Text("Address") }
        )
        TextField(
            value = email.value,
            onValueChange = {email.value = it},
            label = { Text("Email") }
        )

        Button(
            onClick = {
                viewModel.createUser(
                    name = name.value,
                    password = password.value,
                    phoneNumber = phoneNumber.value,
                    pinCode = pinCode.value,
                    address = address.value,
                    email = email.value
                )
            }
        ) {
            Text("Sign Up")
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(16.dp))

        Button(onClick = {
            navController.navigate(Routes.LoginRoute)
        }) {
            Text("Log In")
        }
    }
}





































