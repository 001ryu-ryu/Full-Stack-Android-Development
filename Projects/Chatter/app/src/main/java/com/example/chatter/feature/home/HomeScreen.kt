package com.example.chatter.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.chatter.Routes
import com.example.chatter.feature.auth.signin.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    val signInViewModel: SignInViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val channels = homeViewModel.channels.collectAsState()
    val addChannelDialog = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = channels.value) {
        homeViewModel.getChannels()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log out") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            signInViewModel.logout()
                            navHostController.navigate(Routes.LoginScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addChannelDialog.value = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                item {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {Text("Search messages")},
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                            .padding(8.dp)
                            .background(Color.Gray)
                    )
                }
                items(channels.value) { channel ->
                    Column {
                        Text(channel.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(
                                    onClick = {
                                        navHostController.navigate(
                                            Routes.ChatScreen(channelId = channel.id)
                                        )
                                    }
                                )
                                .background(Color.Red.copy(alpha = 0.3f))
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
    if (addChannelDialog.value) {
        ModalBottomSheet(
            onDismissRequest = {
                addChannelDialog.value = false
            },
            sheetState = sheetState
        ) {
            AddChannelDialog(onAddChannel = { channelName ->
                homeViewModel.addChannel(channelName)
                addChannelDialog.value = false
            })
        }
    }
}

@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {
    val channelName = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Add Channel")
        Spacer(Modifier.padding(8.dp))
        TextField(
            value = channelName.value,
            onValueChange = { channelName.value = it },
            label = { Text("Channel Name") },
            singleLine = true,
        )
        Spacer(Modifier.padding(8.dp))
        Button(
            onClick = {
                onAddChannel(channelName.value)
            }
        ) {
            Text("Add")
        }
    }
}

























