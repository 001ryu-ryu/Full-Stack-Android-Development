package com.example.testex.utils

import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test


class HelperTest {

    lateinit var helper: Helper
    @Before
    fun setUp() {
        helper = Helper()
    }

    @After
    fun tearDown() {
        println("After every test-case")
    }

    @Test
    fun isPallindrome() {
        // Arrange

        // Act
        val result = helper.isPallindrome("level")
        // Assert
        assertEquals(true, result)

    }

    @Test
    fun isPallindrome_inputString_mouse_expectedFalse() {
        // Arrange

        // Act
        val result = helper.isPallindrome("mouse")
        // Assert
        assertEquals(false, result)

    }



}