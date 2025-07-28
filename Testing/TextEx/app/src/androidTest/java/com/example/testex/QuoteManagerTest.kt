package com.example.testex

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.testex.utils.QuoteManager
import com.google.gson.JsonSyntaxException
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException

class QuoteManagerTest {
//    @Before
//    fun setUp() {
//    }
//
//    @After
//    fun tearDown() {
//    }

    @Test
    fun getQuoteList() {
    }

    @Test
    fun setQuoteList() {
    }

    @Test
    fun getCurrentQuoteIndex() {
    }

    @Test
    fun setCurrentQuoteIndex() {
    }

    @Test(expected = FileNotFoundException::class)
    fun populateQuoteFromAssets() {
        val quoteManager = QuoteManager()
        val context = ApplicationProvider.getApplicationContext<Context>()
        quoteManager.populateQuoteFromAssets(context, "")
    }

    @Test(expected = JsonSyntaxException::class)
    fun testPopulateQuoteFromAssets_InvalidJSON_expected_Exception() {
        val quoteManager = QuoteManager()
        val context = ApplicationProvider.getApplicationContext<Context>()
        quoteManager.populateQuoteFromAssets(context, "malformed.json")
    }



    @Test
    fun populateQuotes() {
    }

    @Test
    fun getCurrentQuote() {
    }

    @Test
    fun getNextQuote() {
    }

    @Test
    fun getPreviousQuote() {
    }

}