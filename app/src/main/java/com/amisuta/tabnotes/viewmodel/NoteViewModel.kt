package com.amisuta.tabnotes.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.amisuta.tabnotes.entities.Note
import com.amisuta.tabnotes.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()
    var fetchedNote: LiveData<Note> = MutableLiveData()

    fun insert(title: String, body: String) = viewModelScope.launch {
        val note = Note()
        note.title = title
        note.body = body
        repository.insert(note)
    }

    fun fetch(id: Int) = viewModelScope.launch {
        (fetchedNote as? MutableLiveData)?.value = repository.fetch(id)
    }

    fun update(title: String, body: String) = viewModelScope.launch {
        val note: Note = fetchedNote.value!!
        note.title = title
        note.body = body
        repository.update(note)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
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