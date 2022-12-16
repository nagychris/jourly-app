package com.example.jourlyapp

import android.app.Application
import com.example.jourlyapp.model.journal.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// Wrapper for the database so that it is only created when needed (see https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12)
class JournalApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
}