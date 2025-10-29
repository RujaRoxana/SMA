package com.example.todolist.repository

import com.example.todolist.data.Todo
import com.example.todolist.data.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {
    val todos: Flow<List<Todo>> = dao.getAll()
    suspend fun add(text: String) = dao.insert(Todo(text = text))
    suspend fun toggle(todo: Todo) = dao.update(todo.copy(isDone = !todo.isDone))
    suspend fun remove(todo: Todo) = dao.delete(todo)
}
