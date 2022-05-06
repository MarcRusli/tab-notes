package com.amisuta.tabnotes.repository

import androidx.annotation.WorkerThread
import com.amisuta.tabnotes.dao.NoteDao
import com.amisuta.tabnotes.entities.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun fetch(id: Int): Note = noteDao.fetch(id)

    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(id: Int) {
        noteDao.delete(id)
    }
}