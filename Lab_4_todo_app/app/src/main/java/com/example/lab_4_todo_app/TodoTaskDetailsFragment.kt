package com.example.lab_4_todo_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category
import com.example.lab_4_todo_app.databinding.ActivityMainBinding
import com.example.lab_4_todo_app.model.Todo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.w3c.dom.Text
import java.lang.reflect.Type

class TodoTaskDetailsFragment : Fragment() {

    lateinit var todoTask: TodoTask
    lateinit var dataBase: AppDatabase
    lateinit var todoTaskDao: TodoTaskDao
    lateinit var categoryDao: CategoryDao
    lateinit var description: TextView
    lateinit var status: CheckBox
    lateinit var duration: TextView
    lateinit var title: TextView
    lateinit var category: TextView
    lateinit var categoryClassUnit: Category
    lateinit var todo: Todo

    var todoTaskId: Long = -1
    var todoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
//            todoTaskId = savedInstanceState.getLong(TODO_TASK_ID)!!
            todoId = savedInstanceState.getLong(TODO_ID)
        }
        var bundle: Bundle? = this.arguments
        val listType: Type = object : TypeToken<ArrayList<Todo?>?>() {}.type
        if(bundle != null && !bundle.isEmpty) {
//            todoTaskId = bundle.getLong(TODO_TASK_ID) + 1
            todoId = bundle.getLong(TODO_ID) + 1
            val jsonStringOfTodos: String = bundle.getString("todos", "")
            todo = (Gson().fromJson(jsonStringOfTodos, listType) as List<Todo>).get(todoId.toInt())

            Log.e("Todo's id and title", todo.id.toString() + " and " + todo.title)
        }
//        dataBase = MyApplication.instance.getDataBase()!!
//        todoTaskDao = dataBase.todoTaskDao()
//        categoryDao = dataBase.categoryDao()
//        todoTask = todoTaskDao.getTodoTaskById(todoTaskId)
//        categoryClassUnit = categoryDao.getCategoryById(todoTask.category)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_task_details, container, false)
        initUI(view)
        title.text = todo.title
        description.text = "Descrition"
        status.isChecked = todo.completed == true
        duration.text = "Duration"
        category.text = "User id: " + todo.userId.toString()
//        title.text = todoTask.title.toString()
//        description.text = todoTask.description
//        status.isChecked = todoTask.status == true
//        duration.text = todoTask.duration
//        category.text = categoryClassUnit.title
        return view
    }

    private fun initUI(view: View) {
        title = view.findViewById(R.id.titleTextViewDetailed) as TextView
        category = view.findViewById(R.id.categoryTextViewDetailed) as TextView
        duration = view.findViewById(R.id.durationTextViewDetailed) as TextView
        description = view.findViewById(R.id.descriptionTextViewDetailed) as TextView
        status = view.findViewById(R.id.statusCheckBoxDetailed) as CheckBox
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putLong(TODO_TASK_ID, todoTaskId)
        outState.putLong(TODO_ID, todoId)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodoTaskDetailsFragment()
        private const val TODO_TASK_ID = "todoTaskId"
        private const val TODO_ID = "id"
    }


}