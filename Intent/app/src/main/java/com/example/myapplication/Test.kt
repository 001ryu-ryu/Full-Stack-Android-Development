package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun Test() {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
        "https://www.youtube.com/watch?v=GWNrPJyRTcA"
    ))

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("This is Intent Class")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
            context.startActivity(intent)
            }
        ) {
            Text("Click to open")
        }

    }
}