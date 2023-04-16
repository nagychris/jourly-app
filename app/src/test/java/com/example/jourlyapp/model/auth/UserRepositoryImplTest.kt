package com.example.jourlyapp.model.auth

import android.util.Log
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserRepositoryImplTest {

    private val mockStorageService =
        mockk<EncryptedStorageService>(relaxed = true)

    private val userRepository: UserRepository =
        UserRepositoryImpl(mockStorageService)

    private val userNameKey = "user.name"
    private val passcodeKey = "user.passcode"

    @Before
    fun init() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `getUser returns stored user`() {
        every { mockStorageService.getString(userNameKey) } returns "UserName"
        every { mockStorageService.getString(passcodeKey) } returns "Passcode"

        val nameKeyCaptor = slot<String>()
        val passcodeKeyCaptor = slot<String>()

        val user = userRepository.getUser()

        verifySequence {
            mockStorageService.getString(capture(nameKeyCaptor))
            mockStorageService.getString(capture(passcodeKeyCaptor))
        }

        Assert.assertEquals(userNameKey, nameKeyCaptor.captured)
        Assert.assertEquals(passcodeKey, passcodeKeyCaptor.captured)

        Assert.assertNotNull(user)
        Assert.assertEquals("UserName", user?.name)
        Assert.assertEquals("Passcode", user?.passcode)
    }

    @Test
    fun `getUser returns null if no user stored`() {
        every { mockStorageService.getString(userNameKey) } returns null
        every { mockStorageService.getString(passcodeKey) } returns null

        val nameKeyCaptor = slot<String>()
        val passcodeKeyCaptor = slot<String>()

        val user = userRepository.getUser()

        verifySequence {
            mockStorageService.getString(capture(nameKeyCaptor))
            mockStorageService.getString(capture(passcodeKeyCaptor))
        }

        Assert.assertEquals(userNameKey, nameKeyCaptor.captured)
        Assert.assertEquals(passcodeKey, passcodeKeyCaptor.captured)

        Assert.assertNull(user)
    }

    @Test
    fun `createUser stores user details`() {
        val nameKeyCaptor = slot<String>()
        val nameCaptor = slot<String>()
        val passcodeKeyCaptor = slot<String>()
        val passcodeCaptor = slot<String>()
        val hasKeyCaptor = slot<String>()

        every { mockStorageService.hasKey(userNameKey) } returns false

        val user = userRepository.createUser("UserName", "123")

        verifySequence {
            mockStorageService.hasKey(capture(hasKeyCaptor))
            mockStorageService.storeString(
                capture(nameKeyCaptor),
                capture(nameCaptor)
            )
            mockStorageService.storeString(
                capture(passcodeKeyCaptor),
                capture(passcodeCaptor)
            )
        }

        Assert.assertEquals(userNameKey, hasKeyCaptor.captured)
        Assert.assertEquals(userNameKey, nameKeyCaptor.captured)
        Assert.assertEquals(passcodeKey, passcodeKeyCaptor.captured)
        Assert.assertEquals("UserName", nameCaptor.captured)
        Assert.assertEquals("123", passcodeCaptor.captured)

        Assert.assertNotNull(user)
        Assert.assertEquals("UserName", user?.name)
        Assert.assertEquals("123", user?.passcode)
    }

    @Test
    fun `createUser stores user details with empty passcode`() {
        val nameKeyCaptor = slot<String>()
        val nameCaptor = slot<String>()
        val passcodeKeyCaptor = slot<String>()
        val passcodeCaptor = slot<String>()
        val hasKeyCaptor = slot<String>()

        every { mockStorageService.hasKey(userNameKey) } returns false

        val user = userRepository.createUser("UserName", "")

        verifySequence {
            mockStorageService.hasKey(capture(hasKeyCaptor))
            mockStorageService.storeString(
                capture(nameKeyCaptor),
                capture(nameCaptor)
            )
            mockStorageService.storeString(
                capture(passcodeKeyCaptor),
                capture(passcodeCaptor)
            )
        }

        Assert.assertEquals(userNameKey, hasKeyCaptor.captured)
        Assert.assertEquals(userNameKey, nameKeyCaptor.captured)
        Assert.assertEquals(passcodeKey, passcodeKeyCaptor.captured)
        Assert.assertEquals("UserName", nameCaptor.captured)
        Assert.assertEquals("", passcodeCaptor.captured)

        Assert.assertNotNull(user)
        Assert.assertEquals("UserName", user?.name)
        Assert.assertEquals("", user?.passcode)
    }

    @Test
    fun `createUser returns null if name is empty string`() {
        val user = userRepository.createUser("", "")

        verifySequence(inverse = true) {
            mockStorageService.hasKey(any())
            mockStorageService.storeString(any(), any())
            mockStorageService.storeString(any(), any())
        }

        Assert.assertNull(user)
    }

    @Test
    fun `createUser overwrites user if already stored`() {
        val nameKeyCaptor = slot<String>()
        val nameCaptor = slot<String>()
        val passcodeKeyCaptor = slot<String>()
        val passcodeCaptor = slot<String>()
        val hasKeyCaptor = slot<String>()
        val removePasscodeKeyCapture = slot<String>()
        val removeNameKeyCapture = slot<String>()

        every { mockStorageService.hasKey(userNameKey) } returns true

        val user = userRepository.createUser("UserName", "123")

        verifySequence {
            mockStorageService.hasKey(capture(hasKeyCaptor))
            mockStorageService.removeString(capture(removeNameKeyCapture))
            mockStorageService.removeString(capture(removePasscodeKeyCapture))
            mockStorageService.storeString(
                capture(nameKeyCaptor),
                capture(nameCaptor)
            )
            mockStorageService.storeString(
                capture(passcodeKeyCaptor),
                capture(passcodeCaptor)
            )
        }


        Assert.assertEquals(userNameKey, hasKeyCaptor.captured)
        Assert.assertEquals(userNameKey, removeNameKeyCapture.captured)
        Assert.assertEquals(passcodeKey, removePasscodeKeyCapture.captured)
        Assert.assertEquals(userNameKey, nameKeyCaptor.captured)
        Assert.assertEquals(passcodeKey, passcodeKeyCaptor.captured)
        Assert.assertEquals("UserName", nameCaptor.captured)
        Assert.assertEquals("123", passcodeCaptor.captured)

        Assert.assertNotNull(user)
        Assert.assertEquals("UserName", user?.name)
        Assert.assertEquals("123", user?.passcode)
    }

    @Test
    fun `removeUser removes user details`() {
        val removePasscodeKeyCapture = slot<String>()
        val removeNameKeyCapture = slot<String>()

        userRepository.removeUser()

        verifySequence {
            mockStorageService.removeString(capture(removeNameKeyCapture))
            mockStorageService.removeString(capture(removePasscodeKeyCapture))
        }

        Assert.assertEquals(userNameKey, removeNameKeyCapture.captured)
        Assert.assertEquals(passcodeKey, removePasscodeKeyCapture.captured)
    }
}