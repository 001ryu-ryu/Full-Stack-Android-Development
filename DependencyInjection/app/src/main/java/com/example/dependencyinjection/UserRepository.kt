package com.example.dependencyinjection

import android.util.Log
import javax.inject.Inject

interface UserRepository {
    fun saveUser(email: kotlin.String, password: kotlin.String)
}


class SQLRepository @Inject constructor() : UserRepository {
    override fun saveUser(email: kotlin.String, password: kotlin.String) {
        Log.d("TAG", "User saved in DB")
    }

}

class FireBaseRepository : UserRepository {
    override fun saveUser(email: String, password: String) {
        Log.d("TAG", "User saved in Firebase")
    }

}