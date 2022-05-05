package com.example.lab_4_todo_app.presenter

import android.app.Activity
import android.content.Context
import com.example.lab_4_todo_app.ListOfTasksFragment
import com.example.lab_4_todo_app.R
import com.example.lab_4_todo_app.api.ApiService
import com.example.lab_4_todo_app.contract.ContractInterface.*
import com.example.lab_4_todo_app.model.MainActivityModel
import com.example.lab_4_todo_app.model.Todo
import com.example.lab_4_todo_app.model.TodoTask

class MainActivityPresenter(_view: ContractInterface.View) : ContractInterface.Presenter {

    private var view: ContractInterface.View = _view
    private var model: ContractInterface.Model = MainActivityModel()

    init {
        view.initView()
    }


    override fun getTodoTaskDetails(id: Int, apiService: ApiService): Todo {
        return model.getTodoTaskById(id, apiService)
    }

    override fun getTodoTaskLists(activity: Activity, apiService: ApiService): List<Todo>{
        model.getTodoTasks(apiService)
        return model.getTodoTasksLocal(apiService)
    }

}

