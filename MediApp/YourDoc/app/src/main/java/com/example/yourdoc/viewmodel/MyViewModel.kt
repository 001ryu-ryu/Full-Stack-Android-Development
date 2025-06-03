package com.example.yourdoc.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourdoc.common.ResultState
import com.example.yourdoc.network.response.CreateUserResponse
import com.example.yourdoc.network.response.LogInUserResponse
import com.example.yourdoc.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    val repo = Repo()

    val createUserState = mutableStateOf<CreateUserResponse?>(null)

    fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        pinCode: String,
        address: String,
        email: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.createUser(name, password, phoneNumber, pinCode, address, email)
            createUserState.value = response.body()
        }
    }


   private val _loginUserState = MutableStateFlow(LoginUserState())
    val loginUserState = _loginUserState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.loginUser(email, password).collect {
                when(it) {
                    is ResultState.Loading -> {
                        //_loginUserState.value.isLoading = true  // try later if this works
                        _loginUserState.value = LoginUserState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _loginUserState.value = LoginUserState(error = it.error.message, isLoading = false)
                    }

                    is ResultState.Success -> {
                        _loginUserState.value = LoginUserState(success = it.data, isLoading = false)
                    }
                }

            }
        }
    }
}


data class LoginUserState(
    var isLoading: Boolean = false,
    var success: LogInUserResponse? = null,
    var error: String? = null
)