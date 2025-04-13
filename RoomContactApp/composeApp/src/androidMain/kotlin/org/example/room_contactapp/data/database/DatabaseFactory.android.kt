package org.example.room_contactapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(var ctx: Context) {
    actual fun create(): RoomDatabase.Builder<ContactDatabase> {
        val appContext = ctx.applicationContext
        val dbFile = appContext.getDatabasePath(ContactDatabase.DATABASE_NAME)
        return Room.databaseBuilder<ContactDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}