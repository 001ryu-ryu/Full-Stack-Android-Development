package com.example.contact.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.contact.R
import com.example.contact.model.database.Contact
import com.example.contact.ui.navigation.Routes
import com.example.contact.viewmodel.MyViewModel

@Composable
fun ContactsScreen(modifier: Modifier = Modifier, viewModel: MyViewModel = hiltViewModel(), navHostController: NavHostController) {
    val contacts = viewModel.contacts.collectAsState()


    Scaffold(
        floatingActionButton = {
            AddContactButton {
               navHostController.navigate(Routes.AddContact)
            }
        }
    ) {innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(contacts.value) {
                ContactCard(
                    image = it.image,
                    name = it.name,
                    phoneNumber = it.phoneNumber,
                    favoriteIcon = {
                        IconButton(
                            onClick = {
                                viewModel.updateContact(
                                    it.copy(isFavourite = !(it.isFavourite ?: false))
                                )
                            }
                        ) {
                            if (it.isFavourite == true) {
                                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                            } else {
                                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
                            }
                        }
                    },
                    onDeleteClick = {
                        viewModel.deleteContact(it)
                    },
                    onCardClick = {navHostController.navigate(Routes.IndividualContact(it.id))}
                )
                Spacer(Modifier.height(5.dp))
            }
        }
    }

}

@Composable
fun ContactCard(
    image: ByteArray,
    name: String,
    phoneNumber: String,
    favoriteIcon: @Composable () -> Unit,
    onDeleteClick: () -> Unit,
    onCallClick: () -> Unit = {},
    onCardClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(
                onClick = onCardClick
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                if (image.contentEquals("".toByteArray())) {
                    Image(
                        painter = painterResource(id = R.drawable.default_image),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                } else {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(1f).aspectRatio(1f).clip(RoundedCornerShape(38.dp))
                    )
                }

            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = name,
                        modifier = Modifier.padding(horizontal = 12.dp)
                            .padding(top = 10.dp))
                    Row {
                        IconButton(
                            onClick = onCallClick
                        ) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null)
                        }
                        favoriteIcon()
                        IconButton(
                            onClick = onDeleteClick
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }

                }

                Text(phoneNumber,
                    modifier = Modifier.padding(horizontal = 20.dp)
                        .padding(bottom = 10.dp))

            }

        }

    }
}

@Composable
fun AddContactButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
    }
}

























