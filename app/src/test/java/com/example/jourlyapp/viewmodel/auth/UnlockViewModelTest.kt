package com.example.jourlyapp.viewmodel.auth

import com.example.jourlyapp.model.auth.User
import com.example.jourlyapp.model.auth.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UnlockViewModelTest {

    private val userRepository = mockk<UserRepository>()

    private val viewModel = UnlockViewModel(userRepository)

    @Before
    fun setup() {
        every { userRepository.getUser() } returns User(
            "User",
            "Passcode"
        )
    }

    @Test
    fun `passcode is initially empty`() {
        assertEquals(viewModel.passcode.value, "")
    }

    @Test
    fun `updatePasscode updates passcode`() {
        viewModel.updatePasscode("Passcode")

        assertEquals(viewModel.passcode.value, "Passcode")
    }

    @Test
    fun `isPasscodeValid returns true if passcode is not empty`() {
        assertFalse(viewModel.isPasscodeValid())

        viewModel.updatePasscode("Passcode")

        assertTrue(viewModel.isPasscodeValid())
    }
}
