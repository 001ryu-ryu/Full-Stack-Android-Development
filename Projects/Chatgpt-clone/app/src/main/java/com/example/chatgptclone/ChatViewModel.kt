package com.example.chatgptclone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgptclone.repo.ChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val chatRepo: ChatRepo) : ViewModel() {


    private var chatList = mutableListOf<Chat>()

    var state = MutableStateFlow<List<Chat>>(emptyList())
        private set

    private var streamJob: Job? = null

    val streamFlow = MutableSharedFlow<String>()

    // Streaming assistant message
    private val _streamingAssistantMessage = MutableStateFlow<String?>(null)
    val streamingAssistantMessage = _streamingAssistantMessage.asStateFlow()

    fun createChatCompletion(message: String) {
        Log.d("ChatVM", "createChatCompletion called with message: $message")

        chatList.add(
            Chat(
                message = message,
                messageType = "user"
            )
        )
        state.value = chatList.toList()
        Log.d("ChatVM", "User message added to chatList and state updated")

        // Cancel previous stream if running
        streamJob?.cancel()
        Log.d("ChatVM", "Previous streamJob cancelled (if any)")

        streamJob = viewModelScope.launch {
            var assistantMessage = ""
            _streamingAssistantMessage.value = ""

            Log.d("ChatVM", "Starting new stream from repo")
            chatRepo.streamChatCompletion(message).collect { chunk ->
                Log.d("ChatVM", "Received chunk: $chunk")

                if (!chunk.startsWith("[error]:")) {
                    assistantMessage += chunk
                    streamFlow.emit(chunk)
                    _streamingAssistantMessage.value = assistantMessage
                    Log.d("ChatVM", "Emitting chunk to streamFlow")

                    // Update last assistant message or add a new one
                    if (chatList.lastOrNull()?.messageType == "assistant") {
                        chatList[chatList.lastIndex] = chatList.last().copy(message = assistantMessage)
                        Log.d("ChatVM", "Updated existing assistant message")
                    } else {
                        chatList.add(Chat(message = assistantMessage, messageType = "assistant"))
                        Log.d("ChatVM", "Added new assistant message")
                    }
                    state.value = chatList.toList()
                    Log.d("ChatVM", "State updated with assistant message")
                } else {
                    // Handle error case
                    chatList.add(Chat(message = chunk, messageType = "assistant"))
                    state.value = chatList.toList()
                    _streamingAssistantMessage.value = null
                    Log.e("ChatVM", "Error received from stream: $chunk")
                }
            }
            // After streaming is done, add the full assistant message to the chat list
            if (assistantMessage.isNotEmpty()) {
                if (chatList.lastOrNull()?.messageType == "assistant") {
                    chatList[chatList.lastIndex] = chatList.last().copy(message = assistantMessage)
                } else {
                    chatList.add(Chat(message = assistantMessage, messageType = "assistant"))
                }
                state.value = chatList.toList()
            }
            _streamingAssistantMessage.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        streamJob?.cancel()
        Log.d("ChatVM", "ViewModel cleared and streamJob cancelled")
    }
}
