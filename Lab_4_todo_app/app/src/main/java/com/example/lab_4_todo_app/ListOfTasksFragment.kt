package com.example.lab_4_todo_app

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


class ListOfTasksFragment : Fragment() {
    private lateinit var listOfTodoTasks: ArrayList<TodoTask>
    private lateinit var listOfCategories: ArrayList<Category>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            listOfTodoTasks = savedInstanceState.getParcelableArrayList<TodoTask>(
                LIST_OF_TODO_TASKS)!!
            listOfCategories = savedInstanceState.getParcelableArrayList<Category>(
                LIST_OF_CATEGORIES)!!
        }
        else {
            listOfCategories = ArrayList<Category>()
            listOfCategories.add(Category(1, "StudyRelated"))
            listOfCategories.add(Category(2, "WorkRelated"))
            listOfTodoTasks = ArrayList<TodoTask>()
            for (i in 1..9){
                listOfTodoTasks.add(TodoTask(i, "Do homework" + i, "Prepare to homework" + i, Random.nextBoolean(), listOfCategories.get(1), i.toString()))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list_of_tasks, container, false)
        val context: Context = view.context
        recyclerView = view.findViewById(R.id.recyclerView)
        var todoTaskAdapter = TodoTaskAdapter(listOfTodoTasks, listOfCategories, context)
        var linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = todoTaskAdapter
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(LIST_OF_TODO_TASKS, listOfTodoTasks)
        outState.putParcelableArrayList(LIST_OF_CATEGORIES, listOfCategories)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListOfTasksFragment()
        private const val LIST_OF_CATEGORIES = "listOfCategories"
        private const val LIST_OF_TODO_TASKS = "listOfTodoTasks"
    }

}