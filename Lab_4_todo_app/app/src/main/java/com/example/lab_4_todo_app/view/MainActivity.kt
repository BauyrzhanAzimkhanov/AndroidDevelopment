package com.example.lab_4_todo_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.lab_4_todo_app.AppDatabase
import com.example.lab_4_todo_app.ListOfTasksFragment
import com.example.lab_4_todo_app.MyApplication
import com.example.lab_4_todo_app.R
import com.example.lab_4_todo_app.api.ApiService
import com.example.lab_4_todo_app.contract.ContractInterface.*
import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category
import com.example.lab_4_todo_app.model.Todo
import com.example.lab_4_todo_app.presenter.MainActivityPresenter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class MainActivity : AppCompatActivity(), ContractInterface.View {

    lateinit var dataBase: AppDatabase
    lateinit var todoTaskDao: TodoTaskDao
    lateinit var categoryDao: CategoryDao
    lateinit var apiService: ApiService
    lateinit var todos: List<Todo>
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var listOfTasksFragment: ListOfTasksFragment


    private var presenter: ContractInterface.Presenter? = null

    companion object {
        const val TODOS = "todos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = MyApplication.instance.getApiService()!!
        presenter = MainActivityPresenter(this)
        updateViewData()
        /*dataBase = MyApplication.instance.getDataBase()!!
        todoTaskDao = dataBase.todoTaskDao()
        categoryDao = dataBase.categoryDao()
        val category1 = Category(categoryId = 1, title = "Work related")
        val category2 = Category(categoryId = 2, title = "Stydy related")
        categoryDao.insert(category1)
        categoryDao.insert(category2)
            for (i in 1..20){
                todoTaskDao.insert(TodoTask(title = ("Do homework" + i), description = ("Prepare to homework" + i), status = Random.nextBoolean(), duration = i.toString(), category = category1.categoryId))
            }
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        val listOfTasksFragment = ListOfTasksFragment.newInstance()
        fragmentTransaction.replace(R.id.listFragmentContainer, listOfTasksFragment)
            .addToBackStack("listOfTasksFragment").commit()*/
    }

    override fun initView() {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        listOfTasksFragment = ListOfTasksFragment.newInstance()
//        todos = presenter?.getTodoTaskLists(this, apiService)!!


    }

    override fun updateViewData() {
        presenter?.getTodoTaskLists(this, apiService)!!
        val bundle = Bundle()
        val gson: Gson = Gson()
        val todosStringJson: String = gson.toJson(todos)
        bundle.putString(TODOS, todosStringJson)

        listOfTasksFragment.setArguments(bundle)
        fragmentTransaction.replace(R.id.listFragmentContainer, listOfTasksFragment)
            .addToBackStack("listOfTasksFragment").commit()
    }
}