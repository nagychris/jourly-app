package com.example.jourlyapp.model.journal.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jourlyapp.model.journal.dao.JournalEntryDAO
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair

@Database(
    entities = [
        JournalEntry::class,
        QuestionAnswerPair::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null
        private val lock = Object()
        private val loggerTag = AppDatabase::class.java.simpleName
        private val name = "jourly_database"

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(lock) {
                    Log.i(loggerTag, "Creating DB instance...")
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, name
                    )
                        .build()
                }
            }
            Log.i(loggerTag, "Instance created.")
            return instance
        }

        fun closeInstance() {
            Log.i(loggerTag, "Closing database instance...")
            if (instance != null && instance!!.isOpen) {
                instance!!.openHelper.close()
            }
        }
    }


    // declare DAOs here
    abstract fun journalEntryDao(): JournalEntryDAO
}