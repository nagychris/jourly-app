package com.example.jourlyapp.viewmodel.auth

import com.example.jourlyapp.model.auth.User
import com.example.jourlyapp.model.auth.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RegisterUserViewModelTest {

    private val userRepository = mockk<UserRepository>()

    private val viewModel = RegisterUserViewModel(userRepository)

    @Before
    fun setup() {
        every { userRepository.createUser(any(), any()) } returns User(
            "User",
            "Passcode"
        )
    }

    @Test
    fun `userName and passcode are initialized with empty string`() {
        assertEquals(viewModel.userName.value, "")
        assertEquals(viewModel.userPasscode.value, "")
    }

    @Test
    fun `updateUserName updates name correctly`() {
        viewModel.updateUserName("User")

        assertEquals(viewModel.userName.value, "User")
    }

    @Test
    fun `updateUserPasscode updates name correctly`() {
        viewModel.updateUserPasscode("Passcode")

        assertEquals(viewModel.userPasscode.value, "Passcode")
    }

    @Test
    fun `isValid returns true only if user name not empty`() {
        assertFalse(viewModel.isUserValid())

        viewModel.updateUserName("User")

        assertTrue(viewModel.isUserValid())
    }

    @Test
    fun `createUser calls repository with current values`() {
        viewModel.updateUserName("User")
        viewModel.updateUserPasscode("Passcode")

        viewModel.createUser()

        verify { userRepository.createUser("User", "Passcode") }
    }
}