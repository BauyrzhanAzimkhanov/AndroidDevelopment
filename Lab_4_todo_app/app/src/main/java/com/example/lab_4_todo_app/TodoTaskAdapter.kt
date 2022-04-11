package com.example.lab_4_todo_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.R

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao


//import android.R


class TodoTaskAdapter(val listOfTodoTasks: List<TodoTask>, val listOfCategories: List<Category>, val context: Context) :
    RecyclerView.Adapter<TodoTaskAdapter.TodoTaskViewHolder>() {

    lateinit var dataBase: AppDatabase
    lateinit var categoryDao: CategoryDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskViewHolder {
        dataBase = MyApplication.instance.getDataBase()!!
        categoryDao = dataBase.categoryDao()
        val view = LayoutInflater.from(context).inflate(R.layout.todo_task_recyclerview_item, parent, false)
        return TodoTaskViewHolder(view, listOfTodoTasks, listOfCategories)
    }

    override fun onBindViewHolder(holder: TodoTaskViewHolder, position: Int) {
        val todoTask = listOfTodoTasks.get(position)
        val category = categoryDao.getCategoryById(todoTask.category)
        holder.statusCheckBox.isChecked = todoTask.status == true
        holder.titleTextView.text = todoTask.title
        listOfCategories
        holder.categoryTextView.text = category.title
        holder.titleTextView.setOnClickListener(View.OnClickListener { v ->
            val activity = v.context as AppCompatActivity
            val todoTaskDetailsFragment: Fragment = TodoTaskDetailsFragment()
            val bundle: Bundle = Bundle()
//            bundle.putParcelable("todoTask", todoTask)
            bundle.putLong(TODO_TASK_ID, position.toLong())
            todoTaskDetailsFragment.arguments = bundle
//            todoTaskDetailsFragment.(bundle)
            activity.supportFragmentManager.beginTransaction().replace(R.id.listFragmentContainer, todoTaskDetailsFragment).addToBackStack("todoTaskDetailsFragment").commit()
        })
    }

    override fun getItemCount(): Int {
        return listOfTodoTasks.size
    }

    class TodoTaskViewHolder(view: View, listOfTodoTasks: List<TodoTask>, listOfCategories: List<Category>): RecyclerView.ViewHolder(view) {
        var statusCheckBox: CheckBox = view.findViewById(R.id.statusCheckBox)
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var categoryTextView: TextView = view.findViewById(R.id.categoryTextView)
    }

    companion object {
        const val TODO_TASK_ID = "todoTaskId"
    }

}