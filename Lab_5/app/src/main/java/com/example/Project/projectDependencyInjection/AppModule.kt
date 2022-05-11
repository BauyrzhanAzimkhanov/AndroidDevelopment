package com.example.Project.projectDependencyInjection

import com.example.Project.projectDependencyInjection.appModule
import com.example.Project.projectDependencyInjection.networkModule
import com.example.Project.projectDependencyInjection.repositoryModule

val appModule = listOf(viewModelModule, networkModule, repositoryModule)
