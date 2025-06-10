package com.example.viewmodeldemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodeldemo.viewmodels.LiveDataViewModel

@Composable
fun TopLevel(modifier: Modifier = Modifier, model: LiveDataViewModel = viewModel()) {
    val customerName: String by model.customerName.observeAsState("")

}