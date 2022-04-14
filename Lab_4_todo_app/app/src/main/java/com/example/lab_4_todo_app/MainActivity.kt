package com.example.lab_4_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var dataBase: AppDatabase
    lateinit var todoTaskDao: TodoTaskDao
    lateinit var categoryDao: CategoryDao

    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBase = MyApplication.instance.getDataBase()!!
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
            .addToBackStack("listOfTasksFragment").commit()
    }
}