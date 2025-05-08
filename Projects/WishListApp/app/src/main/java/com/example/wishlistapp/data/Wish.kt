package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String = "",
    @ColumnInfo(name = "wish-desc")
    val description: String = ""
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Marrying Mamtaz Begum", description = "I want to live the rest of my life with my heart"),
        Wish(title = "Spending Time with my beloved girl", description = "I want to live the rest of my life with my sweetheart"),
        Wish(title = "Becoming a great app developer", description = "Getting a job and then give my baby the best life"),
    )
}