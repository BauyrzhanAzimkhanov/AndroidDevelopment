package com.example.Project.projectRoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Project.projectData.models.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodosDatabase: RoomDatabase() {

    abstract fun getTodosDao(): TodosDao

    companion object{
        @Volatile
        private var INSTANCE: TodosDatabase? = null

        fun getTodosDatabase(context: Context): TodosDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){    // we cannot change TodosDatabase until we execute this code block
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodosDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}