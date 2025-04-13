package org.example.room_contactapp.data.database

import androidx.room.RoomDatabase


expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<ContactDatabase>
}