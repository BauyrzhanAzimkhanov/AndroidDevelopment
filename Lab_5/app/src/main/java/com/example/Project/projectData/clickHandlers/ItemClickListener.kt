package com.example.Project.projectData.clickHandlers

import com.example.Project.projectData.models.Todo

interface ItemClickListener {
    fun onItemClick(item: Todo)
    fun onItemMenuClick(item: Todo)
}