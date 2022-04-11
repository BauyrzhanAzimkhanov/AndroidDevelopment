package com.example.lab_4_todo_app

import android.app.Application
import androidx.room.Room

class MyApplication: Application() {
    private var database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "todo_task_app_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun getDataBase(): AppDatabase? {
        return database
    }

    companion object {
        lateinit var instance: MyApplication
    }
}