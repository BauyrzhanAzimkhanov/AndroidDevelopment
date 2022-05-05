package com.example.lab_4_todo_app

import android.R.attr
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random
import androidx.fragment.app.FragmentManager
import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category
import com.example.lab_4_todo_app.model.Todo
import android.R.attr.defaultValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ListOfTasksFragment : Fragment() {
    private lateinit var listOfTodoTasks: List<TodoTask>
    private lateinit var listOfCategories: List<Category>
    private lateinit var todos: List<Todo>
    lateinit var dataBase: AppDatabase
    lateinit var todoTaskDao: TodoTaskDao
    lateinit var categoryDao: CategoryDao
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
        }
        else {
//            listOfCategories = ArrayList<Category>()
//            listOfCategories.add(Category(1, "StudyRelated"))
//            listOfCategories.add(Category(2, "WorkRelated"))
//            listOfTodoTasks = ArrayList<TodoTask>()
//            for (i in 1..9){
//                listOfTodoTasks.add(TodoTask(i, "Do homework" + i, "Prepare to homework" + i, Random.nextBoolean(), listOfCategories.get(1), i.toString()))
//            }
        }
//        dataBase = MyApplication.instance.getDataBase()!!
//        todoTaskDao = dataBase.todoTaskDao()
//        categoryDao = dataBase.categoryDao()
//        listOfTodoTasks = todoTaskDao.getAllTodoTasks()
//        listOfCategories = categoryDao.getAllCategories()
        val bundle = this.arguments
        val listType: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        if (bundle != null && !bundle.isEmpty) {
            val jsonStringOfTodos: String = bundle.getString("todos", "")
            todos = Gson().fromJson(jsonStringOfTodos, listType)
            Log.e("Json size", todos.size.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_of_tasks, container, false)
        val context: Context = view.context
        recyclerView = view.findViewById(R.id.recyclerView)
        var todoAdadpter = TodoAdadpter(todos, context)
//        var todoTaskAdapter = TodoTaskAdapter(listOfTodoTasks, listOfCategories, context)
        var linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
//        recyclerView.adapter = todoTaskAdapter
        recyclerView.adapter = todoAdadpter
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListOfTasksFragment()
        private const val LIST_OF_CATEGORIES = "listOfCategories"
        private const val LIST_OF_TODO_TASKS = "listOfTodoTasks"
    }

}