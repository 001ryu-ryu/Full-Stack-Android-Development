package com.example.firestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.firestore.ui.theme.FireStoreTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private val user = User(
        id = "1",
        name = "Iftikar",
        email = "abc@gmail.com",
        age = 21
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firestoreClient = FirestoreClient()

        setContent {
            FireStoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    firestoreClient.upsertUser(user).collect {
                                        println("FirestoreClient: result: $it")
                                    }
                                }
                            }
                        ) {
                            Text("Insert")
                        }

                        Spacer(Modifier.height(50.dp))

                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    firestoreClient.getUser("abc@gmail.com").collect {
                                        if (it != null) {
                                            println("FirestoreClient: result: $it")
                                        } else {
                                            println("FirestoreClient: user not found")
                                        }

                                    }
                                }
                            }
                        ) {
                            Text("Get")
                        }

                        Spacer(Modifier.height(50.dp))

                        // a button for update
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    firestoreClient.upsertUser(user).collect {
                                        println("FirestoreClient: result: $it")
                                    }
                                }
                            }
                        ) {
                            Text("Update")
                        }
                    }



                }
            }
        }
    }
}
