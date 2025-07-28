package com.example.chatter.model

data class Message(
    val id: String = "",
    val text: String = "",
    val senderId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val senderName: String = "",
    val senderImage: String? = "",
    val imageUrl: String? = ""
)
