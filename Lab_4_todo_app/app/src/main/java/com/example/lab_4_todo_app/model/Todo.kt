package com.example.lab_4_todo_app.model

data class Todo(
    public val completed: Boolean,
    public val id: Int,
    public val title: String,
    public val userId: Int
)
