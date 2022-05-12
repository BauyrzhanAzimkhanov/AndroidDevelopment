package com.example.Project.projectBackendApi

import com.example.Project.projectData.models.Todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>?    // It can be blocked be async call
}