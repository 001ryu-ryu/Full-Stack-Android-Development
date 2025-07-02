package com.example.contact.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.contact.components.NavigateBackTopBar
import com.example.contact.model.database.Contact
import com.example.contact.viewmodel.MyViewModel

@Composable
fun AddContactScreen(
    viewModel: MyViewModel = hiltViewModel(), navHostController: NavHostController
) {
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

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
                        phoneNumber = phoneNumber
                    )
                )
                navHostController.popBackStack()
            },
            isSaveButtonEnabled = isButtonEnabled
        )
    }

}

@Composable
fun AddField(
    modifier: Modifier = Modifier,
    name: String,
    phoneNumber: String,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onAddButtonClick: () -> Unit,
    isSaveButtonEnabled: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = {
                Text("Enter Name")
            }
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChange(it) },
            label = {
                Text("Enter Phone Number")
            }
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





















