package com.example.wishlistapp

//noinspection UsingMaterialAndMaterial3Libraries

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String,
           onBackNavClicked: () -> Unit) {
    val navigationIcon: (@Composable () -> Unit)? =
        {
            if (!title.contains("Wish Here")) {
                IconButton(onClick = {
                    onBackNavClicked()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        tint = Color.White,
                        contentDescription = null

                    )
                }
            } else {
                null
            }

        }
    
    TopAppBar(title = {Text(text = title, color = colorResource(id = R.color.white),
            modifier = Modifier.padding(start = 4.dp).heightIn(max = 24.dp))},
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon
        )
    
}