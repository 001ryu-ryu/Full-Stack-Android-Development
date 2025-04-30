package com.example.yourdoc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yourdoc.viewmodel.MyViewModel

@Composable
fun SignUp(viewModel: MyViewModel) {
    
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val pinCode = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = name.value, onValueChange = {name.value = it}, label = { Text(text = "Name") })
        TextField(value = password.value, onValueChange = {password.value = it}, label = { Text(text = "Password") })
        TextField(value = phoneNumber.value, onValueChange = {phoneNumber.value = it}, label = { Text(text = "Phone Number") })
        TextField(value = email.value, onValueChange = {email.value = it}, label = { Text(text = "Email") })
        TextField(value = pinCode.value, onValueChange = {pinCode.value = it}, label = { Text(text = "Pin Code") })
        TextField(value = address.value, onValueChange = {address.value = it}, label = { Text(text = "Address") })

        Button(onClick = {
            viewModel.createUser(name.value, password.value, phoneNumber.value, email.value, pinCode.value, address.value)
        }) {
            Text(text = "Sign Up")
        }

    }
    
}