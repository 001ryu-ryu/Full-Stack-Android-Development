package com.example.room.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.room.room.Convertors
import com.example.room.room.dao.ContactDao
import com.example.room.room.entity.Contact

@Database(entities = [Contact::class], version = 2)
@TypeConverters(Convertors::class)
abstract class ContactDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactDao


    companion object {

        val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE contact ADD COLUMN isActive INTEGER NOT NULL DEFAULT(1)")
            }
        }

        @Volatile // every threads will know that database has been created
        private var INSTANCE: ContactDataBase? = null

        fun getDatabase(context: Context): ContactDataBase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactDataBase::class.java,
                        "contactDB")
                        .addMigrations(migration_1_2)
                        .build()
                }

            }
            return INSTANCE!!
        }
    }
}