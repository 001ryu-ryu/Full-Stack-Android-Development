package com.example.room.room

import androidx.room.TypeConverter
import java.util.Date

class Convertors {

    @TypeConverter
    fun fromDateToLong(value : Date): Long {
        return value.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long): Date {
        return Date(value)
    }
}