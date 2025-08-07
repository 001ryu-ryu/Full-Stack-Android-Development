package com.example.chatgptclone.response

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)