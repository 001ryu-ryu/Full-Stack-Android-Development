package com.example.chatgptclone

import java.util.Date // Assuming Date might be used later, if not, it can be removed.

data class Chat(
    val message: String,
    val messageType: String, // "user", "assistant"
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val timestamp: Long = System.currentTimeMillis() // Added for unique keys and potential sorting
)
