package com.example.chatgptclone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgptclone.repo.ChatRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val chatRepo = ChatRepo()

    private var chatList = mutableListOf<Chat>()

    var state = MutableStateFlow<List<Chat>>(emptyList())
        private set


    fun createChatCompletion(message: String) {
        chatList.add(
            Chat(
                message = message,
                messageType = "user"
            )
        )
        state.value = chatList.toList()

        viewModelScope.launch {
            val response = chatRepo.createChatCompletion(message)
            chatList.add(
                Chat(
                    message = response,
                    messageType = "assistant"
                )
            )
            state.value = chatList.toList()
        }
    }
}


































