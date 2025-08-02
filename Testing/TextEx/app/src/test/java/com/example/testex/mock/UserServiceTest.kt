package com.example.testex.mock

import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.Test

class UserServiceTest {

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(userRepository.logInUser(anyString(), anyString())).thenReturn(LOGIN_STATUS.SUCCESS)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testUserService() {
        val sut = UserService(userRepository)
        val status = sut.logInUser("abc@mail.com", "awedaj")
        Assert.assertEquals("Success", status)
    }

}