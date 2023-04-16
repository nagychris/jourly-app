package com.example.jourlyapp.model.auth

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.jourlyapp.model.auth.util.FakeAndroidKeyStoreProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class EncryptedStorageServiceTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val encryptedStorageService = EncryptedStorageService(context)

    @Before
    fun setup() {
        // see comment in FakeAndroidKeyStoreProvider
        FakeAndroidKeyStoreProvider.setup()
    }

    @Test
    fun storeStringSavesString() {
        encryptedStorageService.storeString("key", "string")
        assertTrue(encryptedStorageService.hasKey("key"))
        assertEquals("string", encryptedStorageService.getString("key"))
    }

    @Test
    fun hasKeyReturnsFalseIfNoKeyExists() {
        encryptedStorageService.storeString("key", "string")
        assertFalse(encryptedStorageService.hasKey("doesntExist"))
    }

    @Test
    fun hasKeyReturnsTrueIfKeyExists() {
        encryptedStorageService.storeString("key", "string")
        assertTrue(encryptedStorageService.hasKey("key"))
    }

    @Test
    fun removeStringDeletesString() {
        encryptedStorageService.storeString("key", "string")

        assertTrue(encryptedStorageService.hasKey("key"))
        assertEquals("string", encryptedStorageService.getString("key"))

        encryptedStorageService.removeString("key")

        assertFalse(encryptedStorageService.hasKey("key"))
        assertNull(encryptedStorageService.getString("key"))
    }
}