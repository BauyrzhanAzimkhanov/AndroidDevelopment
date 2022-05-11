package com.example.lab_4_todo_app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab_4_todo_app.dao.CategoryDao
import com.example.lab_4_todo_app.dao.TodoTaskDao
import com.example.lab_4_todo_app.model.TodoTask
import com.example.lab_4_todo_app.model.Category

@Database(entities = [TodoTask::class, Category::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoTaskDao(): TodoTaskDao
    abstract fun categoryDao(): CategoryDao
}