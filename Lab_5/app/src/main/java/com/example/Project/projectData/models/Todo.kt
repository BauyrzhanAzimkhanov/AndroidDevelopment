package com.example.Project.projectData.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String?,
    val completed: Boolean = false,
    var actionType: String = ActionType.UNKNOWN.id     // custom query
)
