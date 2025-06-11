package com.example.gesturedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.gesturedemo.ui.theme.GestureDemoTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestureDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MultiTouchDemo(Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    TapPressDemo()
}

//HERE SINGLE TAP, DOUBLE TAP, LONG PRESS
@Composable
fun TapPressDemo(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("Waiting...") }
    val tapHandler = {status: String ->
        textState = status
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .padding(10.dp)
                .background(Color.Blue)
                .size(100.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { tapHandler("onPress Detected") },
                        onDoubleTap = { tapHandler("onDoubleTap Detected") },
                        onLongPress = { tapHandler("onLongPress Detected") },
                        onTap = { tapHandler("onTap Detected") }
                    )
                }
        )

        Spacer(modifier.height(10.dp))
        Text(textState)
    }
}

// HERE DRAG GESTURE
@Composable
fun DragDemo(modifier: Modifier = Modifier) {
    var xOffset by remember { mutableFloatStateOf(0f) }

    Box(
        modifier
            .offset { IntOffset(xOffset.roundToInt(), 0) }
            .size(100.dp)
            .background(Color.Blue)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { distance ->
                    xOffset += distance
                }
            )
    ) {

    }
}

@Composable
fun PointerInputDrag(modifier: Modifier = Modifier) {
    var xOffset by remember { mutableFloatStateOf(0f) }
    var yOffset by remember { mutableFloatStateOf(0f) }

    Box(
        modifier
            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
            .background(Color.Blue)
            .size(100.dp)
            .pointerInput(Unit) {
                detectDragGestures { _, distance ->
                    xOffset += distance.x
                    yOffset += distance.y

                }
            }
    ) {

    }

    Box {

    }
}

// SCROLLING
@Composable
fun ScrollModifiers(modifier: Modifier = Modifier) {
    val image = ImageBitmap.imageResource(id = R.drawable.scenery)

    Box(modifier = modifier
        .size(150.dp)
        .verticalScroll(rememberScrollState())
        .horizontalScroll(rememberScrollState())) {
        Canvas(
            modifier.size(360.dp, 270.dp)
        ) {
            drawImage(
                image = image,

                topLeft = Offset(x = 0f, y = 0f)
            )
        }

    }
}

// PINCH GESTURE
@Composable
fun MultiTouchDemo(modifier: Modifier = Modifier) {
    var scale by remember { mutableFloatStateOf(1f) }
    val state = rememberTransformableState {
        scaleChange, offsetChange, rotationChange ->
        scale *= scaleChange
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                )
                .transformable(state = state)
                .background(Color.Blue)
                .size(100.dp)
        ) {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestureDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ScrollModifiers(Modifier.padding())

        }
    }
}





















