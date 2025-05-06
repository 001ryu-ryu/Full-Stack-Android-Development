package com.example.room.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.room.room.entity.Contact

@Dao
interface ContactDao {
    @Insert
    suspend fun insertContact(contact: Contact)

    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getContacts(): LiveData<List<Contact>>
}