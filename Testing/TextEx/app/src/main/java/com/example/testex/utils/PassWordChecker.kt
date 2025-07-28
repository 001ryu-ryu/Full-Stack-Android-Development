package com.example.testex.utils

class PassWordChecker {
    fun checkPassword(password: String): Boolean {
        if (password.length < 6 || password.isEmpty()) {
            return false
        } else {
            return true
        }
    }
}