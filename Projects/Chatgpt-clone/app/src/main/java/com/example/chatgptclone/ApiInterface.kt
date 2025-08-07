package com.example.chatgptclone

import com.example.chatgptclone.response.ChatRequest
import com.example.chatgptclone.response.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Body chatRequest: ChatRequest
    ) : ChatResponse
}