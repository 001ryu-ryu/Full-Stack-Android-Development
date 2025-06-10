package com.example.viewmodeldemo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodeldemo2.ui.theme.ViewModelDemo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelDemo2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    ScreenSetUp(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun ScreenSetUp(modifier: Modifier = Modifier,
                viewModel: MyViewModel = viewModel()
) {
    MainScreen(
        modifier = modifier,
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.result,
        convertTemp = {
            viewModel.convertTemp(it)
        },
        switchChange = {viewModel.switchChange()}
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier,
               isFahrenheit: Boolean,
               result: String,
               convertTemp: (String) -> Unit,
               switchChange: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        
        var textState by remember { mutableStateOf("") }

        val onTextChange = {text: String ->
            textState = text
        }

        Text("Temperature Converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineSmall)

        InputRow(
            isFahrenheit = isFahrenheit,
            textState = result,
            switchChange = switchChange,
            onTextChange = onTextChange
        )

        Text(result,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineMedium)

        Button(
            onClick = {
                convertTemp(textState)
            }
        ) {
            Text("Convert Temperature")
        }

    }



}

@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    switchChange: () -> Unit,
    onTextChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = {switchChange()}
        )

        OutlinedTextField(
            value = textState,
            onValueChange = {onTextChange(it)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = {Text("Enter temperature")},
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_black_24dp),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        )

        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000)
        ) {
            visible ->
            when (visible) {
                true -> Text(
                    "\u2109", style = MaterialTheme.typography.headlineSmall
                )

                false -> Text(
                    "\u2103", style = MaterialTheme.typography.headlineSmall
                )
            }

        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowPreview() {
    ViewModelDemo2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ScreenSetUp(modifier = Modifier.padding(innerPadding))

        }
    }
}





















