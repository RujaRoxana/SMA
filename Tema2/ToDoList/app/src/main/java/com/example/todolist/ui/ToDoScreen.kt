package com.example.todolist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.Todo
import com.example.todolist.ui.components.AddTodoDialog
import com.example.todolist.ui.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(vm: TodoViewModel = viewModel()) {
    val todos by vm.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("ToDo List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adaugă")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(12.dp)) {
            if (todos.isEmpty()) {
                Text("Nu ai încă task-uri. Apasă + pentru a adăuga.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(todos, key = Todo::id) { todo ->
                        TodoItem(todo, onToggle = { vm.toggle(todo) }, onDelete = { vm.delete(todo) })
                    }
                }
            }
        }
    }

    if (showDialog) AddTodoDialog(
        onDismiss = { showDialog = false },
        onAdd = { vm.addTodo(it); showDialog = false }
    )
}
