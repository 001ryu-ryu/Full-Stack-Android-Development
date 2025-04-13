package org.example.room_contactapp.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object ContactDatabaseConstructor : RoomDatabaseConstructor<ContactDatabase>{
    override fun initialize(): ContactDatabase
}