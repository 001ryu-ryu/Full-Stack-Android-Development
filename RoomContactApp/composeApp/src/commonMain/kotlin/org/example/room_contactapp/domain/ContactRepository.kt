package org.example.room_contactapp.domain

import kotlinx.coroutines.flow.Flow
import org.example.room_contactapp.data.entity.Contact

interface ContactRepository {
    suspend fun getContacts(): Flow<List<Contact>>
    suspend fun upsertContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
}