package com.example.yourdoc.repo

import com.example.yourdoc.common.ResultState
import com.example.yourdoc.network.ApiProvider
import com.example.yourdoc.network.response.LogInUserResponse
import com.example.yourdoc.network.response.UserResponseById
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repo {
    suspend fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        pinCode: String,
        address: String,
        email: String
    ) = ApiProvider.providesApiService().createUser(
        name = name,
        password = password,
        phoneNumber = phoneNumber,
        pinCode = pinCode,
        address = address,
        email = email
    )

    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<LogInUserResponse>> {
        return flow {
            emit(ResultState.Loading)

            try {
                val response = ApiProvider.providesApiService().logInUser(email, password)
                emit(ResultState.Success(response.body()!!))
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }
    }

    suspend fun getUserById (
        userId: String
    ) : Flow<ResultState<UserResponseById>> {
        return flow {
            emit(ResultState.Loading)

            try {
                val response = ApiProvider.providesApiService().getUserById(userId)
                emit(ResultState.Success(response.body()!!))
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }
    }
}




























