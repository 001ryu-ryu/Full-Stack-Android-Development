package com.example.testex.utils

import android.content.Context
import android.content.res.AssetManager
import org.junit.After
import org.junit.Before
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import kotlin.test.Test
import kotlin.test.assertEquals

class QuoteManagerTest {
    @Mock
    lateinit var context: Context

    @Mock
    lateinit var assetManager: AssetManager
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun teardown() {
        // ...
    }

    @Test
    fun test() {
        val testStream = QuoteManagerTest::class.java.getResourceAsStream("/quotes.json")
        doReturn(assetManager).`when`(context).assets // when asset property is accessed, return assetManager
        Mockito.`when`(context.assets.open(anyString())).thenReturn(testStream) // when open method is called, return testStream

        val sut = QuoteManager()
        sut.populateQuoteFromAssets(context, "")
        val quote = sut.getCurrentQuote()
        assertEquals("The only way to do great work is to love what you do.", quote.text)
    }
}

































