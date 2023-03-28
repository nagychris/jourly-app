package com.example.jourlyapp.model.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Provides encrypted, key-value based storage with [EncryptedSharedPreferences].
 * Data is stored locally on the device, using [EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV] for keys and
 * [EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM] for values.
 */
class EncryptedStorageService(appContext: Context) {
    private val fileName: String = "encrypted_storage.txt"
    private val sharedPrefs by lazy { createSharedPreferences(appContext) }

    companion object {
        private var instance: EncryptedStorageService? = null
        fun getInstance(context: Context): EncryptedStorageService {
            if (instance == null) {
                instance = EncryptedStorageService(context)
            }

            return instance!!
        }
    }

    private fun createSharedPreferences(appContext: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            appContext,
            fileName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun storeString(key: String, value: String) {
        sharedPrefs.edit().apply {
            putString(key, value)
        }.apply()
    }

    fun getString(key: String): String? {
        return sharedPrefs.getString(key, null)
    }

    fun removeString(key: String) {
        return sharedPrefs.edit().apply {
            remove(key)
        }.apply()
    }

    fun hasKey(key: String): Boolean {
        return sharedPrefs.contains(key)
    }
}