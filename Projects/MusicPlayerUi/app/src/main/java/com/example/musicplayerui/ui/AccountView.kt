package com.example.musicplayerui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountView() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {

                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp))
                Column {
                    Text("Code Red")
                    Text("@001ryu")
                }
            }
            IconButton(onClick = {
                
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }
        }

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Icon(
                imageVector = Icons.Default.Menu, contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Music Menu")

        }
        Divider()
    }
}