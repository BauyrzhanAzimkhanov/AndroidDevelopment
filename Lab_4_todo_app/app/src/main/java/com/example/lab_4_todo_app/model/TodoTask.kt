package com.example.lab_4_todo_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "todo_tasks")
data class TodoTask(
    @PrimaryKey(autoGenerate = true) var todoTaskId: Long? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "status") var status: Boolean? = null,
    @ColumnInfo(name = "duration") var duration: String? = null,
    @ColumnInfo(name = "category") var category: Long? = null
)