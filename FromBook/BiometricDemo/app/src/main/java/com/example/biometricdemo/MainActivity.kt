package com.example.biometricdemo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.biometricdemo.ui.theme.BiometricDemoTheme

class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiometricDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuthenticationScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier) {
    var supportsBiometrics by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val bioMetricManager = BiometricManager.from(context)
    supportsBiometrics = when (bioMetricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    )) {
        BiometricManager.BIOMETRIC_SUCCESS -> true
        else -> {
            Toast.makeText(context, "Biometric Authentication Unavailable", Toast.LENGTH_LONG).show()
            false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BiometricButton(
            state = supportsBiometrics,
            onClick = {
                authenticateUser(context as FragmentActivity)
            },
            text = "Authenticate"
        )

    }
}

@Composable
fun BiometricButton(state: Boolean, onClick: () -> Unit, text: String) {
    Button(
        enabled = state,
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text)

    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun authenticateUser(context: FragmentActivity) {
    val executor = context.mainExecutor
    val biometricPrompt = BiometricPrompt(
        context,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                Toast.makeText(context, "Authentication Successful", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                Toast.makeText(context, "Authentication Error: $errString", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationFailed() {
                Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show()
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Authentication")
        .setDescription("Use the fingerprint sensor or camera to authenticate")
        .setNegativeButtonText("Cancel")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ).build()
    biometricPrompt.authenticate(promptInfo)
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BiometricDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuthenticationScreen(modifier = Modifier.padding(innerPadding))

        }
    }
}

































