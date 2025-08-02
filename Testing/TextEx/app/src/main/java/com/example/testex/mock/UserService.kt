package com.example.testex.mock

class UserService(private val userRepository: UserRepository) {
    fun logInUser(email: String, password: String): String {
        val status = userRepository.logInUser(email, password)
        return when(status) {
            LOGIN_STATUS.INVALID_USER -> "Invalid user"
            LOGIN_STATUS.INVALID_PASSWORD -> "Invalid password"
            LOGIN_STATUS.UNKNOWN_ERROR -> "Unknown error"
            LOGIN_STATUS.SUCCESS -> "Success"
        }
    }
}