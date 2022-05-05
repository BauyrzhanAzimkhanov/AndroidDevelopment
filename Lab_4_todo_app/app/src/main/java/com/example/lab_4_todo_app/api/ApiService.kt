package com.example.lab_4_todo_app.api

import com.example.lab_4_todo_app.model.Todo
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

interface ApiService {
    @GET("todos/")
    fun getTodos(): Call<List<Todo>>

    @GET("todos/{id}/")
    fun getTodoById(@Path("id") todoId: Int): Call<Todo>
//
//
////    @Headers("Cache-Control: max-age=640000", "User-Agent: My-App-Name")
//    @GET("todos/")
//    fun getTodosByUserId(
//        @Query("completed") completed: Boolean,
//        @Query("userId") userId: Int
//    ): Call<List<Todo>>
//
//
//    @FormUrlEncoded
//    @POST("todos/")
//    fun createTodo(
//        @Field("userId") userId: Int,
//        @Field("title") title: String,
//        @Field("completed") completed: Boolean
//    ): Call<Todo>
//
//
//    @GET
//    fun getTodosWithUrl(@Url url: String): Call<List<Todo>>
}