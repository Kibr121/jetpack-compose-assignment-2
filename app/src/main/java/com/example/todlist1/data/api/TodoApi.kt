package com.example.todlist1.data.api

import com.example.todlist1.data.model.Todo
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
} 