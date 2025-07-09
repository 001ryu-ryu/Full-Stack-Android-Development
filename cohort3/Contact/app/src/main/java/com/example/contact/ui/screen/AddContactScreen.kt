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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
    var name by rememberSaveable { mutableStateOf<String?>(null) }
    var phoneNumber by rememberSaveable { mutableStateOf<String?>(null) }
    var email by rememberSaveable { mutableStateOf<String?>(null) }
    var image by rememberSaveable { mutableStateOf<ByteArray>("".toByteArray()) }
    var imageUri by remember { mutableStateOf<Uri>("".toUri()) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }

    nameError = name?.let {
        when {
            it.isBlank() -> "I think the person obviously has a name!"
            it.length <= 2 -> "Who have less than or 2 characters in their name?"
            it.length > 10 -> "You aren't gonna remember this long name, make it short."
            else -> null
        }
    }

    phoneNumberError = phoneNumber?.let {
        when {
            it.isBlank() -> "You need a number to make call!"
            it.length == 1 -> "I said a number, not a digit"
            it.length <= 4 ->"Are you thinking to store some secret pin?"
            it.length > 15 -> "I bet this is not a contact number!"
            else -> null
        }
    }

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
                    textState = name ?: "",
                    errorState = nameError != null && name?.isNotBlank() == true,
                    supportingText = {
                        Text(nameError.orEmpty())
                    }
                ) { name ->
                    onNameChange(name)
                }
                CustomTextField(
                    title = "Enter phone number",
                    textState = phoneNumber?: "",
                    errorState = phoneNumberError != null && phoneNumber?.isNotBlank() == true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = {
                        Text(phoneNumberError.orEmpty())
                    }
                ) { phone ->
                    onPhoneNumberChange(phone)
                }
                CustomTextField(
                    title = "Enter email (optional)",
                    textState = if (email!=null) email!! else "",
                    errorState = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                ) { email ->
                    onEmailChange(email)
                }

                ElevatedButton(
                    enabled = !(!nameError.isNullOrBlank() || !phoneNumberError.isNullOrBlank()),
                    onClick = {
                        viewModel.addContact(
                            Contact(
                                name = name!!,
                                phoneNumber = phoneNumber!!,
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
    errorState: Boolean,
    supportingText: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = textState,
        label = {Text(title)},
        onValueChange = {onValueChange(it)},
        isError = errorState,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions
    )
}


















