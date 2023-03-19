package com.example.jourlyapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.jourlyapp.model.lock.EncryptedStorageService
import com.example.jourlyapp.utils.FakeAndroidKeyStoreProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class EncryptedSharedPreferencesTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val encryptedStorageService = EncryptedStorageService(context)

    @Before
    fun setup() {
        // see comment in FakeAndroidKeyStoreProvider
        FakeAndroidKeyStoreProvider.setup()
    }

    @Test
    fun storeStringWorksCorrectly() {
        encryptedStorageService.storeString("key", "string")
        assertTrue(encryptedStorageService.hasKey("key"))
        assertEquals("string", encryptedStorageService.getString("key"))
    }

    @Test
    fun hasKeyReturnsFalseIfNoKeyExists() {
        encryptedStorageService.storeString("key", "string")
        assertFalse(encryptedStorageService.hasKey("doesntExist"))
    }

}