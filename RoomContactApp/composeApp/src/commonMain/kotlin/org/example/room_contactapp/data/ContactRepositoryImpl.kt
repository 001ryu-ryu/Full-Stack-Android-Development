package org.example.room_contactapp.data

import kotlinx.coroutines.flow.Flow
import org.example.room_contactapp.data.dao.ContactDao
import org.example.room_contactapp.data.entity.Contact
import org.example.room_contactapp.domain.ContactRepository

class ContactRepositoryImpl(
    private val dao: ContactDao
) : ContactRepository {
    override suspend fun getContacts(): Flow<List<Contact>> {
        return dao.getContacts()
    }

    override suspend fun upsertContact(contact: Contact) {
        return dao.upsertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        return dao.deleteContact(contact)
    }
}