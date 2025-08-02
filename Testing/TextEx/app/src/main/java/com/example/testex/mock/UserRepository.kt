package com.example.testex.mock

class UserRepository {
    val users = listOf(
        User(1, "Alice", "alice@example.com", "password1"),
        User(2, "Bob", "bob@example.com", "password2"),
        User(3, "Charlie", "charlie@example.com", "password3")
    )

    fun logInUser( email: String, password: String): LOGIN_STATUS {
        val users = users.filter { user -> user.email == email }
        return if (users.size == 1) {
            if (users[0].password == password) {
                LOGIN_STATUS.SUCCESS
            } else {
                LOGIN_STATUS.INVALID_PASSWORD
            }
        } else {
            LOGIN_STATUS.INVALID_USER
        }
    }
}