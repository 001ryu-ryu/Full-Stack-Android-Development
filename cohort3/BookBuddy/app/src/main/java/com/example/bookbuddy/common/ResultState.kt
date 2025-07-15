package com.example.bookbuddy.common

sealed class ResultState<out T> {
    data class Success<out T>(val data: T): ResultState<T>()
    data class Failure<T>(val error: Exception): ResultState<T>()
    object Loading: ResultState<Nothing>()
}