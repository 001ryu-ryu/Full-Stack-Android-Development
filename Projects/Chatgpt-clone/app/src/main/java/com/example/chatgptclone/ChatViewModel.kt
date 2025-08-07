package com.example.chatgptclone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgptclone.repo.ChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepo) : ViewModel() {

    private val _chatHistoryState = MutableStateFlow<List<Chat>>(emptyList())
    val chatHistoryState = _chatHistoryState.asStateFlow()

    // Represents the raw, accumulating text from the current stream for potential separate UI display
    private val _streamingAssistantMessage = MutableStateFlow<String?>(null)
    val streamingAssistantMessage = _streamingAssistantMessage.asStateFlow()

    private var currentStreamJob: Job? = null

    fun createChatCompletion(userMessageText: String) {
        currentStreamJob?.cancel() // Cancel any existing stream

        // 1. Add user message to history
        val userChat = Chat(message = userMessageText, messageType = "user")
        _chatHistoryState.value = _chatHistoryState.value + userChat

        // Prepare message history for the repository
        // Important: Create history from the *current* state *before* adding placeholders
        val messagesHistoryForRepo = _chatHistoryState.value.map { chat ->
            Pair(chat.messageType, chat.message)
        }

        _streamingAssistantMessage.value = "" // Signal start of streaming for any separate UI observer

        currentStreamJob = viewModelScope.launch {
            var accumulatedResponse = ""
            var assistantMessageAdded = false

            try {
                repository.streamChatCompletionWithHistory(messagesHistoryForRepo)
                    .flowOn(Dispatchers.IO)
                    .onEach { chunk ->
                        if (chunk.startsWith("[error]:")) {
                            Log.e("ChatVM", "Stream error chunk: $chunk")
                            accumulatedResponse += chunk // Or handle as a distinct error message
                            // Update or add error message in history
                            if (assistantMessageAdded && _chatHistoryState.value.lastOrNull()?.messageType == "assistant") {
                                val lastChat = _chatHistoryState.value.last()
                                _chatHistoryState.value = _chatHistoryState.value.dropLast(1) + lastChat.copy(message = accumulatedResponse)
                            } else {
                                _chatHistoryState.value = _chatHistoryState.value + Chat(message = accumulatedResponse, messageType = "assistant")
                                assistantMessageAdded = true
                            }
                            _streamingAssistantMessage.value = accumulatedResponse // Reflect error in raw stream too
                            currentStreamJob?.cancel() // Stop further processing on handled error
                            return@onEach
                        }

                        accumulatedResponse += chunk
                        _streamingAssistantMessage.value = accumulatedResponse

                        if (!assistantMessageAdded) {
                            // First chunk, add new assistant message
                            _chatHistoryState.value = _chatHistoryState.value + Chat(message = accumulatedResponse, messageType = "assistant")
                            assistantMessageAdded = true
                        } else {
                            // Subsequent chunks, update existing assistant message
                            val lastChat = _chatHistoryState.value.last()
                            // Ensure we are updating the assistant's message
                            if (lastChat.messageType == "assistant") {
                                _chatHistoryState.value = _chatHistoryState.value.dropLast(1) + lastChat.copy(message = accumulatedResponse)
                            } else {
                                // This case should ideally not happen if logic is correct, but as a fallback:
                                _chatHistoryState.value = _chatHistoryState.value + Chat(message = accumulatedResponse, messageType = "assistant")
                            }
                        }
                    }
                    .catch { e ->
                        Log.e("ChatVM", "Error in stream collection: ${e.message}", e)
                        val errorMessage = "Error: ${e.message ?: "Unknown streaming error"}"
                        if (assistantMessageAdded && _chatHistoryState.value.lastOrNull()?.messageType == "assistant") {
                            val lastChat = _chatHistoryState.value.last()
                            _chatHistoryState.value = _chatHistoryState.value.dropLast(1) + lastChat.copy(message = errorMessage)
                        } else {
                            _chatHistoryState.value = _chatHistoryState.value + Chat(message = errorMessage, messageType = "assistant")
                        }
                        _streamingAssistantMessage.value = null // Clear raw stream on error
                    }
                    .onCompletion {
                        // This will be called on successful completion or cancellation (not due to an exception caught by .catch)
                        _streamingAssistantMessage.value = null // Clear raw stream
                        Log.d("ChatVM", "Stream completed.")
                    }
                    .collect() // Start collecting the flow
            } catch (e: Exception) {
                // Catch exceptions from launching the flow itself (e.g., if repository.stream... throws directly)
                Log.e("ChatVM", "Error launching stream: ${e.message}", e)
                val errorMessage = "Failed to start chat: ${e.message ?: "Unknown error"}"
                _chatHistoryState.value = _chatHistoryState.value + Chat(message = errorMessage, messageType = "assistant")
                _streamingAssistantMessage.value = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentStreamJob?.cancel()
        Log.d("ChatVM", "ViewModel cleared, stream job cancelled.")
    }
}
