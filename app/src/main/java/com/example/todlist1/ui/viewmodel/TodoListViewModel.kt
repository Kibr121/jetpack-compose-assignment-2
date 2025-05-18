package com.example.todlist1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todlist1.data.model.Todo
import com.example.todlist1.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoListUiState>(TodoListUiState.Loading)
    val uiState: StateFlow<TodoListUiState> = _uiState

    init {
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            repository.getTodos()
                .catch { e ->
                    _uiState.value = TodoListUiState.Error(e.message ?: "Unknown error occurred")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { todos ->
                            _uiState.value = TodoListUiState.Success(todos)
                        },
                        onFailure = { error ->
                            _uiState.value = TodoListUiState.Error(error.message ?: "Unknown error occurred")
                        }
                    )
                }
        }
    }
}

sealed class TodoListUiState {
    data object Loading : TodoListUiState()
    data class Success(val todos: List<Todo>) : TodoListUiState()
    data class Error(val message: String) : TodoListUiState()
} 