package com.example.testex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.testex.room.QuoteDao
import com.example.testex.room.QuoteDatabase
import com.example.testex.room.QuoteRoom
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuotesDaoTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var quoteDatabase: QuoteDatabase
    lateinit var quoteDao: QuoteDao
    @Before
    fun setUp() {
        quoteDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            QuoteDatabase::class.java
        ).allowMainThreadQueries().build()

        quoteDao = quoteDatabase.getDao()
    }

    @Test
    fun insertQuote_expectedSingleQuote() = runBlocking{
        val quote = QuoteRoom( 0, "This is a test quote", "Ryu")
        quoteDao.insertQuote(quote)
        val quotes = quoteDao.getQuotes().first()
        assertEquals(1, quotes.size)
        assertEquals("This is a test quote", quotes[0].text)
    }

    @Test
    fun deleteQuote_expectedNoResults() = runBlocking{
        val quote = QuoteRoom( 0, "This is a test quote", "Ryu")
        quoteDao.insertQuote(quote)
        quoteDao.deleteAllQuotes()
        val quotes = quoteDao.getQuotes().first()
        assertEquals(0, quotes.size)

    }



    @After
    fun tearDown() {
        quoteDatabase.close()
    }
}










































