package com.example.todlist1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todlist1.data.model.Todo
import com.example.todlist1.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoDetailUiState {
    object Loading : TodoDetailUiState()
    data class Success(val todo: Todo) : TodoDetailUiState()
    data class Error(val message: String) : TodoDetailUiState()
}

class TodoDetailViewModel(
    private val repository: TodoRepository,
    private val todoId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow<TodoDetailUiState>(TodoDetailUiState.Loading)
    val uiState: StateFlow<TodoDetailUiState> = _uiState

    init { loadTodo() }

    fun loadTodo() {
        viewModelScope.launch {
            try {
                val todo = repository.getTodoById(todoId)
                if (todo != null) _uiState.value = TodoDetailUiState.Success(todo)
                else _uiState.value = TodoDetailUiState.Error("Todo not found")
            } catch (e: Exception) {
                _uiState.value = TodoDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
} 