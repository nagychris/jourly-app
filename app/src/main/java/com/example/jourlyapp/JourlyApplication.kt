package com.example.jourlyapp

import android.app.Application
import com.example.jourlyapp.model.auth.EncryptedStorageService
import com.example.jourlyapp.model.auth.UserRepositoryImpl
import com.example.jourlyapp.model.journal.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Wrapper for the database so that it is only created when needed. This means accessing the database through the
 * [database] reference should be the only way!
 * See: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
 */
class JourlyApplication : Application() {
    private val coroutineScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, coroutineScope) }

    val encryptedStorageService by lazy { EncryptedStorageService(this) }

    val userRepository by lazy { UserRepositoryImpl(encryptedStorageService) }
}