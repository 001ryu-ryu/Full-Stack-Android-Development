package org.example.room_contactapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val favourite: Boolean,
    val timeStamp: Long,
    val address: String
)