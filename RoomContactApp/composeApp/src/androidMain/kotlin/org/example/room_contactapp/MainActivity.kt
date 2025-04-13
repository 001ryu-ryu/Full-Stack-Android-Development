package org.example.room_contactapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.example.room_contactapp.data.database.DatabaseFactory
import org.example.room_contactapp.presentation.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dbBuilder = remember { mutableStateOf(DatabaseFactory(applicationContext).create()) }
            App(dbBuilder.value)
        }
    }
}

