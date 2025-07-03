package com.example.contact.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.contact.R
import com.example.contact.components.NavigateBackTopBar
import com.example.contact.model.database.Contact
import com.example.contact.viewmodel.MyViewModel

@Composable
fun AddContactScreen(
    viewModel: MyViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
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
    ) {
        AddField(
            modifier = Modifier.padding(it),
            imageUri = imageUri,
            name = name,
            phoneNumber = phoneNumber,
            onNameChange = {name ->
                onNameChange(name)
                isButtonEnabled = true
//                isButtonEnabledEvent(name.isEmpty() && phoneNumber.isEmpty())
            },
            onPhoneNumberChange = {phoneNumber ->
                onPhoneNumberChange(phoneNumber.toString())
                isButtonEnabled = true
//                isButtonEnabledEvent(name.isEmpty() && phoneNumber.isEmpty())
            },
            onAddButtonClick = {
                viewModel.addContact(
                    Contact(
                        name = name,
                        phoneNumber = phoneNumber,
                        image = image
                    )
                )
                navHostController.popBackStack()
            },
            isSaveButtonEnabled = isButtonEnabled,
            onImagePickerButtonClick = {
                pickImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        )
    }

}

@Composable
fun AddField(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    name: String,
    phoneNumber: String,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onAddButtonClick: () -> Unit,
    isSaveButtonEnabled: Boolean,
    onImagePickerButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
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
                onClick = onImagePickerButtonClick
            ) {
                Text("Select Image")
            }
        }
        TextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = {
                Text("Enter Name")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChange(it) },
            label = {
                Text("Enter Phone Number")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                enabled = isSaveButtonEnabled,
                onClick = onAddButtonClick,
            ) {
                Text("Add Contact")
            }
        }
    }
}





















