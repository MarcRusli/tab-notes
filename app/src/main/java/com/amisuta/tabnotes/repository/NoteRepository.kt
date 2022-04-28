package com.amisuta.tabnotes.repository

import android.util.Log
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

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}