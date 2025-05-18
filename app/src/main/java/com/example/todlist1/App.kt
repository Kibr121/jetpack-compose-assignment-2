package com.example.todlist1

import android.app.Application
import com.example.todlist1.data.api.TodoApi
import com.example.todlist1.data.local.TodoDatabase
import com.example.todlist1.data.repository.TodoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var repository: TodoRepository
    override fun onCreate() {
        super.onCreate()
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(TodoApi::class.java)
        val db = TodoDatabase.getDatabase(this)
        repository = TodoRepository(api, db.todoDao())
    }
} 