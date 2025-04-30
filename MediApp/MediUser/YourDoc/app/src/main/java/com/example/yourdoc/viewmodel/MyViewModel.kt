package com.example.yourdoc.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdoc.repo.Repo
import com.example.yourdoc.response.CreateUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    val repo = Repo()
    val createUserState = mutableStateOf<CreateUserResponse?>(null)

    fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pinCode: String,
        address: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.createUser(name, password, phoneNumber, email, pinCode, address)
            createUserState.value = response.body()
        }

    }
}