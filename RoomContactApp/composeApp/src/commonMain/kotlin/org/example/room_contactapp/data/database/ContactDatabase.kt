package org.example.room_contactapp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.room_contactapp.data.dao.ContactDao
import org.example.room_contactapp.data.entity.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(ContactDatabaseConstructor::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao

    companion object{
        const val DATABASE_NAME = "contacts.db"
    }
}