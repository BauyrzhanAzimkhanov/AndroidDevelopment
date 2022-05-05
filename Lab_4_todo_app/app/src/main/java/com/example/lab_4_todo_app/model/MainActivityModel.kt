package com.example.lab_4_todo_app.model

import android.util.Log
import com.example.lab_4_todo_app.api.ApiService
import com.example.lab_4_todo_app.contract.ContractInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityModel : ContractInterface.ContractInterface.Model {

    private var todoTasks: List<Todo>? = null
//    private lateinit var todoTasks: List<TodoTask>
    private var todoTask: Todo? = null

//    override fun getCounter()= mCounter
//
//    override fun incrementCounter() {
//        mCounter++
//    }

    override fun getTodoTasks(apiService: ApiService) {
        apiService.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }

            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                Log.e("Response size: ", response.body()!!.size.toString() + "")
            }
        })
//        return todoTasks!!
    }

    override fun getTodoTasksLocal(apiService: ApiService): List<Todo> {
        if(todoTask == null) {
            val b: Todo = Todo(id = 1, completed = true, title = "error", userId = 5)
            val a: List<Todo> = arrayListOf(b)
            return a
        }
        return todoTasks!!
    }

    override fun getCategories(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getTodoTaskById(id: Int, apiService: ApiService): Todo {
        apiService.getTodoById(id).enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                todoTask = response.body()!!
                Log.e("Success get todo by id", response.body()!!.id.toString() + " " + response.body()!!.title.toString())
            }
            override fun onFailure(call: Call<Todo>, t: Throwable) {
                Log.e("Error", "getTodoById() error." + t.toString())
            }
        })
        return todoTask!!
    }

    override fun getCategoryById(id: Int): Category {
        TODO("Not yet implemented")
    }

}

//apiService.getTodos().enqueue(object : Callback<List<Todo>> {
//            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
//                Log.e("Success get todos", response.body()!!.size.toString())
//            }
//
//            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
//                Log.e("Error", "getTodos() error." + t.toString())
//            }
//        })
//        apiService.getTodoById(1).enqueue(object : Callback<Todo> {
//            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
//                Log.e("Success get todo by id", response.body()!!.id.toString() + " " + response.body()!!.title.toString())
//            }
//
//            override fun onFailure(call: Call<Todo>, t: Throwable) {
//                Log.e("Error", "getTodoById() error." + t.toString())
//            }
//        })
//        apiService.getTodosByUserId(completed = true, userId = 4).enqueue(object : Callback<List<Todo>> {
//            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
//                Log.e("Success get todo by id", response.body()!!.size.toString() + ". ")
//            }
//
//            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
//                Log.e("Error", "getTodosByUserId() error." + t.toString())
//            }
//        })
//        apiService.createTodo(userId = 15, title = "Defend project.", completed = false).enqueue(object : Callback<Todo> {
//            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
//                Log.e("Success get todo by id", response.body()!!.id.toString() + " " + response.body()!!.title.toString())
//            }
//
//            override fun onFailure(call: Call<Todo>, t: Throwable) {
//                Log.e("Error", "createTodo() error." + t.toString())
//            }
//        })
//        apiService.getTodosWithUrl("https://jsonplaceholder.typicode.com/todos/").enqueue(object : Callback<List<Todo>> {
//            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
//                Log.e("Success get todo by id", response.body()!!.size.toString() + ". ")
//            }
//
//            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
//                Log.e("Error", "getTodosWithUrl() error." + t.toString())
//            }
//        })