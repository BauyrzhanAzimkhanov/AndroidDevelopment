package com.example.lab_4_todo_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lab_4_todo_app.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT *  FROM categories")
    fun getAllCategories(): List<Category>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getCategoryById(categoryId: Long?): Category

    @Insert
    fun insert(category: Category): Long
}