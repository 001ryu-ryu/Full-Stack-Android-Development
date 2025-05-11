package com.example.mathsirs.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mathsirs.viewmodels.NameViewModel
import com.example.mathsirs.viewmodels.SelectionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen() {
    val selectionViewModel: SelectionViewModel = hiltViewModel()
    val name = selectionViewModel.teacherName
    val pic = selectionViewModel.teacherProfilePicture

    Scaffold(

    ) {
        Column {
            NameScreen(name)
            Spacer(Modifier.height(8.dp))

            Log.d("DetailScreen", "Profile picture URL: $pic")

            Text(text = pic)

            
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

