package com.amisuta.tabnotes.viewmodel

import android.util.Log
import kotlinx.coroutines.launch
import com.amisuta.tabnotes.entities.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.amisuta.tabnotes.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun insert(title: String, body: String) = viewModelScope.launch {
        val note = Note()
        note.title = title
        note.body = body
        try {
            repository.insert(note)
            Log.d("Marc", "it worked!")
        } catch(e: Exception) {
            Log.d("Marc", "ExceptionStuff: " + e)
        }
    }

}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}