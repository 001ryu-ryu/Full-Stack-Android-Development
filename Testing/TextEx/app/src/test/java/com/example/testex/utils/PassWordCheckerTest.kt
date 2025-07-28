package com.example.testex.utils

import org.junit.Assert.*
import org.junit.Test

class PassWordCheckerTest {
    @Test
    fun checkPassword() {
        val passWordChecker = PassWordChecker()
        assertEquals(false, passWordChecker.checkPassword("abc"))
    }

}