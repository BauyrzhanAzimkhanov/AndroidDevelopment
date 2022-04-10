package com.example.lab_4_todo_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import kotlin.properties.Delegates
//import android.R

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import androidx.core.content.ContextCompat


//import android.R





class TodoTaskAdapter(val listOfTodoTasks: ArrayList<TodoTask>, val listOfCategories: ArrayList<Category>, val context: Context) :
    RecyclerView.Adapter<TodoTaskAdapter.TodoTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_task_recyclerview_item, parent, false)
        return TodoTaskViewHolder(view, listOfTodoTasks, listOfCategories)
    }

    override fun onBindViewHolder(holder: TodoTaskViewHolder, position: Int) {
        val todoTask = listOfTodoTasks.get(position)
        holder.statusCheckBox.isChecked = todoTask.status
        holder.titleTextView.text = todoTask.title
        holder.categoryTextView.text = todoTask.category?.title
        holder.titleTextView.setOnClickListener(View.OnClickListener { v ->
            val activity = v.context as AppCompatActivity
            val todoTaskDetailsFragment: Fragment = TodoTaskDetailsFragment()
            val bundle: Bundle = Bundle()
            bundle.putParcelable("todoTask", todoTask)
            todoTaskDetailsFragment.arguments = bundle
//            todoTaskDetailsFragment.(bundle)
            activity.supportFragmentManager.beginTransaction().replace(R.id.listFragmentContainer, todoTaskDetailsFragment).addToBackStack("todoTaskDetailsFragment").commit()
        })
    }

    override fun getItemCount(): Int {
        return listOfTodoTasks.size
    }

    class TodoTaskViewHolder(view: View, listOfTodoTasks: ArrayList<TodoTask>, listOfCategories: ArrayList<Category>): RecyclerView.ViewHolder(view) {
        var statusCheckBox: CheckBox = view.findViewById(R.id.statusCheckBox)
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var categoryTextView: TextView = view.findViewById(R.id.categoryTextView)
    }

}