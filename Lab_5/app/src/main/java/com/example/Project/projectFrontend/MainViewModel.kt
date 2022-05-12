package com.example.Project.projectFrontend

import android.app.Application
import androidx.lifecycle.*
import com.example.Project.projectFragments.ParentViewModel
import com.example.Project.projectData.clickHandlers.ItemClickListener
import com.example.Project.projectData.models.ActionType
import com.example.Project.projectData.models.Todo
import com.example.Project.projectRoomDB.TodosDatabase
import com.example.Project.projectRoomDB.TodoRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : ParentViewModel() {

    private val _getAllFromApi = MutableLiveData<List<Todo>>()
    val getAllFromApi: LiveData<List<Todo>>
        get() = _getAllFromApi

    var getAllFromDb: LiveData<List<Todo>>
    var isDatabaseEmpty: LiveData<List<Todo>>

    private val roomRepository: TodoRoomRepository

    companion object {
        const val USER_ID = 2
    }

    init {
        val todoDao = TodosDatabase.getTodosDatabase(app).getTodosDao()
        roomRepository = TodoRoomRepository(todoDao)
        getAllFromDb = roomRepository.getTodos
        isDatabaseEmpty = roomRepository.getTodos
    }

    fun filterByUserId(list: List<Todo>) {
        val temp = mutableListOf<Todo>()

        list.forEach {
            if (it.userId == USER_ID) {
                temp.add(it.copy(actionType = ActionType.UNKNOWN.id))
            }
        }

        if (temp.isNotEmpty()) {
            addAllToDatabase(temp)
        }
    }

    fun initRecyclerList(list: List<Todo>? = null) {
        if (list == null) {
            getAllFromDb = roomRepository.getTodos
        } else {
            _getAllFromApi.value = list
        }
        //list postValue
    }

    //room Db operations
    private fun addAllToDatabase(list: List<Todo>) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addAllTodos(
                list
            )
        }
        initRecyclerList(list)
    }

    fun addToDatabase(item: Todo?) {
        if (item != null) {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepository.addTodo(
                    item
                )
            }
        }
    }

    fun updateTodo(item: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.update(item)
        }
        initRecyclerList()
    }

    fun deleteTodo(item: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.delete(item)
        }
        initRecyclerList()
    }

    fun getTodoById(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getTodoById(itemId)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteAll()
        }
    }

    override fun handleError(e: Throwable) {

    }

    //listener
    val onItemClickListener = object :
        ItemClickListener {
        override fun onItemClick(item: Todo) {
            updateTodo(item.copy(actionType = ActionType.UNKNOWN.id, completed = !item.completed))
        }

        override fun onItemMenuClick(item: Todo) {
            when (item.actionType) {
                ActionType.UNKNOWN.id -> {

                }
                ActionType.DELETE.id -> {
                    deleteTodo(item.copy(actionType = ActionType.UNKNOWN.id))
                }
                ActionType.UPDATE.id -> {
//                    updateTodo(item.copy(actionType = ActionType.UNKNOWN.id))
                }
            }
        }
    }
}