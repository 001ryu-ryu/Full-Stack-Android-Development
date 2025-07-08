package com.example.contact.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null
                    )
                }
            }

        }
    ) { innerPadding ->
        ContactDetails(
            modifier = Modifier.padding(innerPadding),
            image = contact.value?.image ?: "".toByteArray(),
            name = contact.value?.name ?: "Default",
            phoneNumber = contact.value?.phoneNumber ?: "Default",
            email = contact.value?.email ?: "Not provided"
        )
    }
}

@Composable
fun ContactDetails(modifier: Modifier = Modifier,
                   image: ByteArray,
                   name: String,
                   phoneNumber: String,
                   email: String) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (image.contentEquals("".toByteArray())) {
            Image(
                painter = painterResource(id = R.drawable.default_image),
                contentDescription = null,
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(5.dp))
            )
        } else {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(5.dp)
                ))
        }
        CustomCard(
            name = name,
            phone = phoneNumber,
            email = email
        )
    }
}

@Composable
fun CustomText(
    desc: String,
    detail: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(desc, style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp))
        Spacer(Modifier.height(5.dp))
        Text(text = detail, style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp))
    }
}

@Composable
fun CustomCard(
    name: String,
    phone: String,
    email: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
    ) {
        CustomText(
            desc = "Name",
            detail = name
        )
        Spacer(Modifier.height(15.dp))
        CustomText(
            desc = "Phone Number",
            detail = phone,
        )
        Spacer(Modifier.height(15.dp))
        CustomText(
            desc = "Email",
            detail = email
        )
    }
}










