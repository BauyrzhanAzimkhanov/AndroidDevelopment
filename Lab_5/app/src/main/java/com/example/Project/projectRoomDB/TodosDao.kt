package com.example.Project.projectRoomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Project.projectData.models.Todo


@Dao
interface TodosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Todo>?)

    @Update
    fun update(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE id =:itemId")
    fun getTodoById(itemId: Int): LiveData<Todo?>

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getTodos(): LiveData<List<Todo>>

    @Query("DELETE FROM todo_table")
    fun deleteAll()
}