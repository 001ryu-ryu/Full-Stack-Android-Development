package org.example.room_contactapp.data.entity

data class Contact(

    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val favourite: Boolean,
    val timeStamp: Long,
    val address: String
)