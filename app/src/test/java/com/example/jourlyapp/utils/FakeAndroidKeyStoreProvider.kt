package com.example.jourlyapp.utils

import java.security.Provider
import java.security.Security

/**
 * Provides a fake implementation of AndroidKeyStore within the JVM, instead of an Android device.
 * Credits: https://medium.com/@wujingwe/write-unit-test-which-has-androidkeystore-dependency-f12181ae6311
 */
internal class FakeAndroidKeyStoreProvider : Provider(
    "AndroidKeyStore",
    1.0,
    "Fake AndroidKeyStore provider"
) {

    init {
        put(
            "KeyStore.AndroidKeyStore",
            FakeKeyStore::class.java.name
        )
        put(
            "KeyGenerator.AES",
            FakeKeyGenerator::class.java.name
        )
    }

    companion object {
        fun setup() {
            Security.addProvider(FakeAndroidKeyStoreProvider())
        }
    }
}