package com.example.todolist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Todo
import com.example.todolist.data.TodoDatabase
import com.example.todolist.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = TodoRepository(TodoDatabase.getInstance(app).todoDao())
    val uiState = repository.todos.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun addTodo(text: String) = viewModelScope.launch { repository.add(text) }
    fun toggle(todo: Todo) = viewModelScope.launch { repository.toggle(todo) }
    fun delete(todo: Todo) = viewModelScope.launch { repository.remove(todo) }
}
