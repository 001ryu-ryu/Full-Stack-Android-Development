package com.example.testex.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert
    suspend fun insertQuote(quote: QuoteRoom)

    @Update
    suspend fun updateQuote(quoteRoom: QuoteRoom)

    @Query("SELECT * FROM QuoteRoom")
    fun getQuotes(): Flow<List<QuoteRoom>>

    @Delete
    suspend fun deleteQuote(quote: QuoteRoom)

    @Query("SELECT * FROM QuoteRoom WHERE id = :quoteId")
    suspend fun getQuoteById(quoteId: Int): QuoteRoom

    @Query("DELETE FROM QuoteRoom")
    suspend fun deleteAllQuotes()

}