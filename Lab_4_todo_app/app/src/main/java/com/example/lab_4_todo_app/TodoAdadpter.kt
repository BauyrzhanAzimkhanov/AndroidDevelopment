package com.example.lab_4_todo_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_4_todo_app.model.Todo
import com.example.lab_4_todo_app.view.MainActivity
import com.google.gson.Gson


class TodoAdadpter (val todos: List<Todo>, val context: Context) :
    RecyclerView.Adapter<TodoAdadpter.TodoViewHolder>() {

//    lateinit var dataBase: AppDatabase
//    lateinit var categoryDao: CategoryDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_task_recyclerview_item, parent, false)
        return TodoViewHolder(view, todos)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos.get(position)
        holder.statusCheckBox.isChecked = todo.completed == true
        holder.titleTextView.text = todo.title
        holder.categoryTextView.text = todo.userId.toString()
        holder.titleTextView.setOnClickListener(View.OnClickListener { v ->
            val activity = v.context as AppCompatActivity
            val todoTaskDetailsFragment: Fragment = TodoTaskDetailsFragment()
            val bundle: Bundle = Bundle()
            val gson: Gson = Gson()
            val todosStringJson: String = gson.toJson(todos)
            bundle.putString(MainActivity.TODOS, todosStringJson)
            bundle.putLong(TODO_ID, position.toLong())
            todoTaskDetailsFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.listFragmentContainer, todoTaskDetailsFragment).addToBackStack("todoTaskDetailsFragment").commit()
        })
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    class TodoViewHolder(view: View, todos: List<Todo>): RecyclerView.ViewHolder(view) {
        var statusCheckBox: CheckBox = view.findViewById(R.id.statusCheckBox)
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var categoryTextView: TextView = view.findViewById(R.id.categoryTextView)
    }

    companion object {
        const val TODO_ID = "id"
    }

}