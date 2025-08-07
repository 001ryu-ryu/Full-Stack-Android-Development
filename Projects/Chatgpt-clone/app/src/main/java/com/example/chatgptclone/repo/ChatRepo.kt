package com.example.chatgptclone.repo

import android.util.Log
import com.example.chatgptclone.CHAT_GPT_MODEL
import com.example.chatgptclone.response.ChatRequest
import com.example.chatgptclone.response.ChatResponse
import com.example.chatgptclone.response.Message
import com.example.chatgptclone.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRepo {
    private val apiClient = ApiClient.getInstance()

    suspend fun createChatCompletion(message: String): String {
        Log.d("ChatGpt-Response", "Repo fun initiated")
        val request = ChatRequest(
            messages = listOf(
                Message(
                    content = "Your name is Ryu, an expert and a genius of Mathematics",
                    role = "system"
                ),
                Message(
                    content = message,
                    role = "user"
                )
            ),
            model = CHAT_GPT_MODEL
        )
        val response = apiClient.createChatCompletion(request)
        Log.d("ChatGpt-Response", response.choices.firstOrNull()?.message?.content ?: "No reply")
        return response.choices.firstOrNull()?.message?.content ?: "No reply"
        }
}