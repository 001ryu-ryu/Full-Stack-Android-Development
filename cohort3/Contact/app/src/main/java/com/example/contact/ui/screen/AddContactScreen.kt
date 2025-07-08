package com.example.contact.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.contact.R
import com.example.contact.components.NavigateBackTopBar
import com.example.contact.model.database.Contact
import com.example.contact.utils.Utils
import com.example.contact.viewmodel.MyViewModel

@Composable
fun AddContactScreen(
    viewModel: MyViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf<String?>(null) }
    var image by rememberSaveable { mutableStateOf<ByteArray>("".toByteArray()) }
    var imageUri by remember { mutableStateOf<Uri>("".toUri()) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            imageUri = uri

            val inputStream = uri.let {
                context.contentResolver.openInputStream(it)
            }
            val byteArray = inputStream?.readBytes()
            image = byteArray ?: "".toByteArray()
        }
    }

    val onNameChange = { enteredName: String ->
        name = enteredName
    }

    val onPhoneNumberChange = { enteredPhoneNumber: String ->
        phoneNumber = enteredPhoneNumber
    }
    val onEmailChange = { enteredEmail: String? ->
        email = enteredEmail
    }


    var isButtonEnabled by rememberSaveable { mutableStateOf(false) }

//    val isButtonEnabledEvent = {isEnabled: Boolean ->
//        isButtonEnabled = isEnabled
//    }

    Scaffold(
        topBar = {
            NavigateBackTopBar(
                title = {Text("New Contact")}
            ) {
                navHostController.popBackStack()
            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageUri == "".toUri()) {
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                } else {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
                Button(
                    onClick = {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Text("Select Image")
                }
                Spacer(Modifier.height(5.dp))
                CustomTextField(
                    title = "Enter name",
                    textState = name
                ) { name ->
                    onNameChange(name)
                }
                CustomTextField(
                    title = "Enter phone number",
                    textState = phoneNumber
                ) { phone ->
                    onPhoneNumberChange(phone)
                }
                CustomTextField(
                    title = "Enter email (optional)",
                    textState = if (email!=null) email!! else ""
                ) { email ->
                    onEmailChange(email)
                }

                ElevatedButton(
                    onClick = {
                        viewModel.addContact(
                            Contact(
                                name = name,
                                phoneNumber = phoneNumber,
                                email = email,
                                image = Utils.compressImage(image) ?: "".toByteArray()
                            )
                        )
                        navHostController.popBackStack()
                    }
                ) {
                    Text("Add Contact")
                }
        }

        }
    }

}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onValueChange: (String) -> Unit

) {
    OutlinedTextField(
        value = textState,
        label = {Text(title)},
        onValueChange = {onValueChange(it)}
    )
}


















