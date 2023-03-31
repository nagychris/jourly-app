package com.example.jourlyapp.model.journal

import com.example.jourlyapp.model.journal.dao.JournalDao
import com.example.jourlyapp.model.journal.entities.JournalEntry
import kotlinx.coroutines.flow.Flow

class JournalRepository(journalDao: JournalDao) {

    val journalEntries: Flow<List<JournalEntry>> = journalDao.getEntries()

}