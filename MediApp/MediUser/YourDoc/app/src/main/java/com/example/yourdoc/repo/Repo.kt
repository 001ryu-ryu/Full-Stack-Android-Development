package com.example.yourdoc.repo

import com.example.yourdoc.network.ApiProvider

class Repo {
    suspend fun createUser(
        name : String,
        password : String,
        phoneNumber : String,
        email: String,
        pinCode: String,
        address: String
    ) = ApiProvider.providerApiService().createUser(name, password, phoneNumber, email, pinCode, address)
}