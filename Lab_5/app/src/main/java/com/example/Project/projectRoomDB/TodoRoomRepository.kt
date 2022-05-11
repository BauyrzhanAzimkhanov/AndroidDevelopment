package com.example.Project.projectRoomDB

import androidx.lifecycle.LiveData
import com.example.Project.projectData.models.Todo

class TodoRoomRepository(private val todosDao: TodosDao) {    // provide us convenient functions to work with RoomDB's daos.

    val getTodos: LiveData<List<Todo>> = todosDao.getTodos()

    suspend fun addTodo(item:Todo){
        todosDao.addTodo(item)
    }

    suspend fun addAllTodos(list: List<Todo>){
        todosDao.insertAll(list)
    }

    fun update(item: Todo){
        todosDao.update(item)
    }

    fun getTodoById(itemId: Int){
        todosDao.getTodoById(itemId)
    }

    fun delete(item: Todo){
        todosDao.delete(item)
    }

    fun deleteAll(){
        todosDao.deleteAll()
    }
}