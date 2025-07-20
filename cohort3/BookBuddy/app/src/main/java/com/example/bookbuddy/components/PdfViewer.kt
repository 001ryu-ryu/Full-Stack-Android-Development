package com.example.bookbuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.VerticalPdfReaderState


@Composable
fun PdfScreen(
    url: String,
) {
    val state = VerticalPdfReaderState(
        resource = ResourceType.Remote(url),
        isZoomEnable = true
    )
    Column {
        VerticalPDFReader(
            state = state,
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Gray)
        )
    }
}
