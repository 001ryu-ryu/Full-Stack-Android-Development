package com.example.contact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.model.database.Contact
import com.example.contact.model.database.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val contactDatabase: ContactDatabase) : ViewModel(){
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()

    private val _individualContact = MutableStateFlow<Contact?>(null)
    val individualContact = _individualContact.asStateFlow()

    init {
        getAllContacts()
    }

    fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactDatabase.getDao().getAllContacts().collect { contacts ->
                _contacts.value = contacts
            }
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDatabase.getDao().upsertContact(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDatabase.getDao().updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDatabase.getDao().deleteContact(contact)
        }
    }

    fun getIndividualContact(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val contact = contactDatabase.getDao().getIndividualContact(id)
            _individualContact.value = contact
        }
    }

    fun setFavorite(isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _individualContact.value?.isFavourite = isFavorite
        }
    }
}













