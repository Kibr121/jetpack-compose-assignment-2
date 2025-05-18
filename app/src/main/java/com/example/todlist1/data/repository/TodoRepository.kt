package com.example.todlist1.data.repository

import com.example.todlist1.data.api.TodoApi
import com.example.todlist1.data.local.TodoDao
import com.example.todlist1.data.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class TodoRepository(
    private val api: TodoApi,
    private val dao: TodoDao
) {
    fun getTodos(): Flow<Result<List<Todo>>> = flow {
        // Emit cached data first
        val cachedTodos = dao.getAllTodos().first()
        emit(Result.success(cachedTodos))

        try {
            // Fetch fresh data from API
            val todos = api.getTodos()
            // Cache the fresh data
            dao.insertTodos(todos)
            emit(Result.success(todos))
        } catch (e: HttpException) {
            emit(Result.failure(e))
        } catch (e: IOException) {
            emit(Result.failure(e))
        }
    }

    suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }
}
