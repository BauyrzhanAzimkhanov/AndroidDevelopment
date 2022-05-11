package com.example.Project.projectBackendApi

import com.example.Project.projectData.models.Todo
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("todos/")
    fun getTodos(): Deferred<Response<List<Todo>>>    // waiting for asynchronous response

}