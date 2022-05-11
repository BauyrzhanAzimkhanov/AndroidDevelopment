package com.example.lab_4_todo_app.contract

import android.app.Activity
import android.content.Context
import com.example.lab_4_todo_app.api.ApiService
import com.example.lab_4_todo_app.model.Category
import com.example.lab_4_todo_app.model.Todo

interface ContractInterface {
    interface ContractInterface {

        interface View {
            fun initView()
            fun updateViewData()
        }

        interface Presenter {
            fun getTodoTaskDetails(id: Int, apiService: ApiService): Todo
            fun getTodoTaskLists(activity: Activity, apiService: ApiService): List<Todo>
        }

        interface Model {
            fun getTodoTasks(apiService: ApiService)
            fun getTodoTasksLocal(apiService: ApiService): List<Todo>
            fun getCategories(): List<Category>
            fun getTodoTaskById(id: Int, apiService: ApiService): Todo
            fun getCategoryById(id: Int): Category
        }
    }
}