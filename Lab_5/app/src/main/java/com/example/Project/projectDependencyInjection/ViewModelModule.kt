package com.example.Project.projectDependencyInjection

import com.example.Project.projectFrontend.TodoViewModel
import com.example.Project.projectFrontend.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {    // we initialize our view model through koin
    viewModel { MainViewModel(app = get()) }
    viewModel { TodoViewModel(todoRepository = get()) }
}
