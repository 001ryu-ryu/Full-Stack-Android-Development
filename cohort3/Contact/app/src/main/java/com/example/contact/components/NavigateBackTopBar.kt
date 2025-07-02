package com.example.contact.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateBackTopBar(
    title: @Composable () -> Unit,
    onClick: () -> Unit
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = onClick
                ))
        }
    )
}