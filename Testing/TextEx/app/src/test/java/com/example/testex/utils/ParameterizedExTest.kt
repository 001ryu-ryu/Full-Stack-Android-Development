package com.example.testex.utils

import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.Test

@RunWith(Parameterized::class)
class ParameterizedExTest(val input: String, val expected: Boolean) {
    @Test
    fun test() {
        val helper = Helper()
        val result = helper.isPallindrome(input)
        assertEquals(expected, result)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "input = {0}, expected = {1}")
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf("level", true),
                arrayOf("mouse", false),
                arrayOf("", true),
                arrayOf("a", true)
            )
        }
    }
}