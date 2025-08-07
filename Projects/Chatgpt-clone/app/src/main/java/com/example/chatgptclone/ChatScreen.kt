package com.example.chatgptclone

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier) {
    val viewModel: ChatViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    var message by remember { mutableStateOf("") }
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
            Box(
            )
            {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    reverseLayout = true
                ) {
                    items(state.value.asReversed()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (it.messageType == "user") {
                                Alignment.CenterEnd
                            } else {
                                Alignment.CenterStart
                            }
                        ) {
                            Card {
                                Text(it.message)
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it }
                )
                Button(
                    onClick = {
                        viewModel.createChatCompletion(message)
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }
}
