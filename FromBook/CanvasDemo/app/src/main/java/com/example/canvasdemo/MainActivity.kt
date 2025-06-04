package com.example.canvasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.canvasdemo.ui.theme.CanvasDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
//    DrawLine()
    DrawRect()
}

@Composable
fun DrawRect() {
    Canvas(
        modifier = Modifier.size(300.dp)
    ) {
        //val size = Size(200.dp.toPx(), 100.dp.toPx())
        inset(100f, 200f) {

            drawRect(
                color = Color.Blue,
                topLeft = Offset(x = 350f, y = 300f),
                size = size / 2f
            )
        }
    }
}


@Composable
fun DrawLine() {
    Canvas(
      modifier = Modifier.size(300.dp)
    ) {
        val height = size.height
        val width = size.width

        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = width, y = height),
            color = Color.Blue,
            strokeWidth = 16.0f,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(30f, 10f, 10f, 10f), phase = 0f
            )
        )
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvasDemoTheme {
        CanvasDemoTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MainScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}


















