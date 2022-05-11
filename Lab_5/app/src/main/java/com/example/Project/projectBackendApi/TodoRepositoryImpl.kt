package com.example.Project.projectBackendApi

import com.example.Project.projectData.models.Todo

class TodoRepositoryImpl(private val todoApi: TodoApi) : TodoRepository {

    override suspend fun getTodos(): List<Todo>? {
        return todoApi.getTodos().await().body()
    }
}