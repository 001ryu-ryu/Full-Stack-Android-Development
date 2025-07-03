package com.example.contact.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.contact.R
import com.example.contact.components.NavigateBackTopBar
import com.example.contact.model.database.Contact
import com.example.contact.viewmodel.MyViewModel
import kotlinx.coroutines.flow.filter

@Composable
fun ContactDetailsScreen(viewModel: MyViewModel = hiltViewModel(), contactId: Int, navHostController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getIndividualContact(contactId)
    }
    val contact = viewModel.individualContact.collectAsState()

    Scaffold(
        topBar = {
            NavigateBackTopBar(
                title = { Text(text = contact.value?.name ?: "Default") }
            ) {
                navHostController.popBackStack()
            }
        }
    ) { innerPadding ->
        ContactDetails(
            modifier = Modifier.padding(innerPadding),
            image = contact.value?.image ?: "".toByteArray(),
            name = contact.value?.name ?: "Default",
            phoneNumber = contact.value?.phoneNumber ?: "Default"
        )
    }
}

@Composable
fun ContactDetails(modifier: Modifier = Modifier,
                   image: ByteArray,
                   name: String,
                   phoneNumber: String) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (image.contentEquals("".toByteArray())) {
            Image(
                painter = painterResource(id = R.drawable.default_image),
                contentDescription = null
            )
        } else {
            AsyncImage(
                model = image,
                contentDescription = null
            )
        }
        CustomText(
            desc = "Name",
            detail = name
        )
        HorizontalDivider(Modifier.fillMaxWidth())
        CustomText(
            modifier = modifier,
            desc = "Phone Number",
            detail = phoneNumber
        )

    }
}

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    desc: String,
    detail: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(desc, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(5.dp))
        Text(text = detail, style = MaterialTheme.typography.bodyLarge)
    }
}












