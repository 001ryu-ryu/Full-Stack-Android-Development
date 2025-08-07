package com.example.chatgptclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val viewModel: ChatViewModel = hiltViewModel()
    val chatHistory by viewModel.chatHistoryState.collectAsState()
    var message by remember { mutableStateOf("") }
    // val streamingAssistantMessage by viewModel.streamingAssistantMessage.collectAsState() // Kept if needed for other UI effects

    val listState = rememberLazyListState()

    // Automatically scroll to the bottom when chatHistory changes
    LaunchedEffect(chatHistory) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(0) // chatHistory is reversed in LazyColumn
        }
    }

    Scaffold(
        containerColor = Color.DarkGray,
        topBar = {
            TopAppBar(
                title = { Text("Chat gpt") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f) // Ensures LazyColumn takes available space
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                reverseLayout = true // Newest messages at the bottom
            ) {
                // Use chatHistory directly; ViewModel updates it progressively.
                // It's important that Chat data class has stable IDs for keys if possible.
                // Using index as key is not ideal but a fallback if Chat items don't have unique IDs.
                // If Chat has a timestamp or unique ID, use it: items(chatHistory.asReversed(), key = { chat -> chat.id })
                items(chatHistory.asReversed()) { chat ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = if (chat.messageType == "user") {
                            Alignment.CenterEnd
                        } else {
                            Alignment.CenterStart
                        }
                    ) {
                        Card(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = chat.message,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        if (message.isNotBlank()) {
                            viewModel.createChatCompletion(message)
                            message = "" // Clear message input after sending
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Send")
                }
            }
        }
    }
}
