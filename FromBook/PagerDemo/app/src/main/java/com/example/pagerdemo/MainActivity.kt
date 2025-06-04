package com.example.pagerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pagerdemo.ui.theme.PagerDemoTheme

val dummyData = listOf(
    "Image 1",
    "Image 2",
    "Image 3",
    "Image 4",
    "Image 5"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PagerDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    CoverPager(modifier)
}

@Composable
fun CoverPager(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState { dummyData.size }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) {
        page ->
        Text(dummyData[page])
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PagerPreview() {
    PagerDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(modifier = Modifier.padding(innerPadding))

        }
    }
}

































