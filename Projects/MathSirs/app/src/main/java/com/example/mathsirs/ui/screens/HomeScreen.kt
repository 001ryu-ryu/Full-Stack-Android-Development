package com.example.mathsirs.ui.screens

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mathsirs.viewmodels.NameViewModel
import com.example.mathsirs.viewmodels.ProfilePictureViewmodel


@Composable
fun ProfilePictureScreen(onClick: (name: String) -> Unit) {
    
    //here we will need to create a viewmodel object cuz to show the data in items
    val profilePictureViewmodel: ProfilePictureViewmodel = hiltViewModel() // this viewmodel method will create an object of ProfilePictureViewmodel and assign it to the variable
    val nameViewmodel: NameViewModel = hiltViewModel()
    val names: State<List<String>> = nameViewmodel.name.collectAsState()
    val profilePictures: State<List<String>> = profilePictureViewmodel.profilePicture.collectAsState()
    // collectAsState() jese jese state update hota rahefa wese wese rerender and update the state
    if (profilePictures.value.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            items(names.value.zip(profilePictures.value)) {
                (name, profilePicture) ->
                ProfilePicture(name, profilePicture, onClick)
//                items(names.value.zip(profilePictures.value)) { (name, profilePicture) ->
//                    ProfilePicture(name = name, profilePicture = profilePicture) { }
//                }

            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun ProfilePicture(name: String?, profilePicture: String?, onClick: (name: String) -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(4.dp).
        clickable {
            onClick(name!!)
        }
            .size(144.dp)
            .clip(RoundedCornerShape(8.dp))
            .paint(painter = rememberAsyncImagePainter(profilePicture))
            .border(1.dp, Color(0xFFC822F1)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = name!!, modifier = Modifier
            .padding(0.dp, 20.dp),
            fontSize = 18.sp, style = MaterialTheme.typography.bodyMedium)
    }
}