package com.example.tema_5.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tema_5.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repo: NoteRepository
) : ViewModel() {

    var notes by mutableStateOf(listOf<String>())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            isLoading = true
            notes = repo.getNotes()
            isLoading = false
        }
    }

    fun addNote(text: String) {
        viewModelScope.launch {
            repo.addNote(text)
            loadNotes()
        }
    }
}
