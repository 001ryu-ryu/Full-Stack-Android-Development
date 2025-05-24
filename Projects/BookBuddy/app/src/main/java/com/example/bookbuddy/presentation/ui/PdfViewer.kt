package com.example.bookbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
fun PdfViewer(url: String) {
    var isDarkMode by remember { mutableStateOf(true) }

    val pdfState = rememberVerticalPdfReaderState(resource = ResourceType.Remote(url), isZoomEnable = true)
    VerticalPDFReader(state = pdfState,
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Gray))
}