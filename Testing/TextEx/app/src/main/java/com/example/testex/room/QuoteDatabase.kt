package com.example.testex.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuoteRoom::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun getDao(): QuoteDao
}