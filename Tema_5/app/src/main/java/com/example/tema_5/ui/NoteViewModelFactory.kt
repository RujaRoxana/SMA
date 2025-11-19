package com.example.tema_5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tema_5.data.repository.NoteRepository

class NoteViewModelFactory(
    private val repo: NoteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repo) as T
    }
}
