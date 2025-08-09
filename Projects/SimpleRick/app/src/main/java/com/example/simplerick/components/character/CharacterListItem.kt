package com.example.simplerick.components.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.network.models.domain.Character
import com.example.simplerick.components.common.CharacterImage

@Composable
fun CharacterListItem(
    modifier: Modifier,
    character: Character,
    characterDataPoints: List<DataPoint>,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(140.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(Color.Transparent, Color.Cyan.copy(alpha = 0.6f))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                onClick = onClick
            )
    ) {
        Box() {
            CharacterImage(
                character.imageUrl, modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            CharacterStatusCircle(character.status, modifier = modifier)
        }
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2)
        ) {
            items(
                items = listOf(DataPoint(title = "Name", description = character.name)) + characterDataPoints,
                key = { it.hashCode() }
            ) { dataPoint ->
                Column(verticalArrangement = Arrangement.Center) {
                    DataPointComponent(dataPoint = dataPoint)
                }
            }
        }
    }
}

























