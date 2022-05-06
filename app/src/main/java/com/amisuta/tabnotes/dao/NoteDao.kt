package com.amisuta.tabnotes.dao

import  androidx.room.*
import com.amisuta.tabnotes.entities.Note
import android.util.Log
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun fetch(id: Int): Note

    @Update
    suspend fun update(note: Note)
}