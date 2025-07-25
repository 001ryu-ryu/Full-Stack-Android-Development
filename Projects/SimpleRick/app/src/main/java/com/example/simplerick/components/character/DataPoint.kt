package com.example.simplerick.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column {
        Text(
            text = dataPoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Cyan
        )
        Text(
            text = dataPoint.description,
            fontSize = 24.sp,
            color = Color.DarkGray
        )

    }
}

@Preview
@Composable
fun PreviewData() {
    DataPointComponent(
        DataPoint(
            title = "RIck",
            description = "Morty"
        )
    )
}