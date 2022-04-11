package com.example.lab_4_todo_app.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.lab_4_todo_app.model.Category
import com.example.lab_4_todo_app.model.TodoTask

data class CategoryWithTodoTasks(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "category"
    )
    val todoTasks: ArrayList<TodoTask>
)