package com.example.mathsirs.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mathsirs.viewmodels.NameViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen() {
    val nameViewModel: NameViewModel = hiltViewModel()
    val teacherName = nameViewModel.teacherName

    Scaffold(

    ) {
        Column {
            NameScreen(teacherName.uppercase())
            Text(text = "Hello")
        }

 }


}

@Composable
fun NameScreen(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        content = {
            Text(text = name.uppercase(),
                modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge)
        }
    ) 
}

