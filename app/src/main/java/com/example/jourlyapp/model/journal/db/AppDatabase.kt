package com.example.jourlyapp.model.journal.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.db.AppDatabase.AppDatabaseCallback
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.JournalQuestion
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random

/**
 * Local persistence database that stores the data of the app in a structured manner (SQLite) using the Room library.
 * Singleton class to prevent duplicate instantiation, as it is rather costly.
 * Pre-populated with dummy data using [AppDatabaseCallback].
 */
@Database(
    entities = [
        JournalEntry::class,
        QuestionAnswerPair::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // declare DAOs here
    abstract fun journalDao(): JournalDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Object()
        private const val DB_NAME = "jourly_database.db"
        private const val TAG = "AppDatabase"

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(LOCK) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        // NOTE: this will remove and re-add dummy data each time the app is started
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                fillDatabaseWithDummyData(database)
            }
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                fillDatabaseWithDummyData(database)
            }
        }

        private fun fillDatabaseWithDummyData(database: AppDatabase) {
            scope.launch {
                val journalDao = database.journalDao()

                // clear contents
                journalDao.deleteAllQuestionAnswerPairs()
                journalDao.deleteAllEntries()
                Log.d(TAG, "Deleted all entries from DB")

                val dummyEntries = getDummyEntries()
                journalDao.insertAllEntries(dummyEntries)

                journalDao.insertAllQAPairs(
                    getDummyQuestionAnswerPairs(
                        dummyEntries.size
                    )
                )
            }
        }

        private fun getDummyEntries(): List<JournalEntry> {
            val list = mutableListOf<JournalEntry>()
            repeat(30) {
                list.add(
                    JournalEntry(
                        it,
                        LocalDateTime.now().minusDays(it.toLong()),
                        Mood.values()[Random.nextInt(1, Mood.values().size)]
                    )
                )
            }
            return list
        }

        private fun getDummyQuestionAnswerPairs(journalEntriesCount: Int): List<QuestionAnswerPair> {
            val list = mutableListOf<QuestionAnswerPair>()
            repeat(60) {
                val qaPair = QuestionAnswerPair(
                    null,
                    Random.nextInt(0, journalEntriesCount),
                    JournalQuestion.values()[Random.nextInt(JournalQuestion.values().size)].toString(),
                    ""
                )
                list.add(qaPair)
            }
            return list
        }
    }
}