package com.example.jourlyapp.model.journal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import com.example.jourlyapp.model.journal.entities.QuestionAnswerPair
import com.example.jourlyapp.model.journal.enums.JournalQuestion
import com.example.jourlyapp.model.journal.enums.Mood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Database(
    entities = [
        JournalEntry::class,
        QuestionAnswerPair::class
    ],
    version = 1,
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
        private const val DB_NAME = "jourly_database"

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(LOCK) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // used to pre-populate database with dummy entries
    // TODO: remove when actual data is used!
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val journalDao = database.journalDao()

                    // clear contents
                    journalDao.deleteAllEntries()
                    journalDao.deleteAllQuestionAnswerPairs()

                    // insert dummy entries and QA-pairs
                    getDummyEntries().forEach {
                        journalDao.insertEntry(it)
                    }
                    getDummyQuestionAnswerPairs().forEach {
                        journalDao.insertQuestionAnswerPair(it)
                    }
                }
            }
        }

        private fun getDummyEntries(): List<JournalEntry> {
            return listOf(
                JournalEntry(null, LocalDateTime.now(), Mood.Good),
                JournalEntry(null, LocalDateTime.now().minusDays(1), Mood.Bad),
                JournalEntry(null, LocalDateTime.now().minusDays(2), Mood.Great),
            )
        }

        private fun getDummyQuestionAnswerPairs(): List<QuestionAnswerPair> {
            return listOf(
                QuestionAnswerPair(null, 1, JournalQuestion.QUESTION1.toString(), null),
                QuestionAnswerPair(null, 1, JournalQuestion.QUESTION2.toString(), null),
                QuestionAnswerPair(null, 1, JournalQuestion.QUESTION3.toString(), null),
                QuestionAnswerPair(null, 2, JournalQuestion.QUESTION1.toString(), null),
                QuestionAnswerPair(null, 2, JournalQuestion.QUESTION2.toString(), null),
                QuestionAnswerPair(null, 2, JournalQuestion.QUESTION3.toString(), null),
                QuestionAnswerPair(null, 3, JournalQuestion.QUESTION1.toString(), null),
                QuestionAnswerPair(null, 3, JournalQuestion.QUESTION2.toString(), null),
                QuestionAnswerPair(null, 3, JournalQuestion.QUESTION3.toString(), null),
            )
        }
    }
}