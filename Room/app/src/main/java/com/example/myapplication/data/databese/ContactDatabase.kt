package com.example.myapplication.data.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.entity.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract var contactDao: ContactDao
}