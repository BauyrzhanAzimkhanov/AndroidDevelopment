package com.example.Project.projectFrontend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Project.projectExceptions.ConnectionFailedException
import com.example.Project.projectExceptions.launchSafe
import com.example.Project.projectFragments.ParentViewModel
import com.example.Project.projectData.models.Todo
import com.example.Project.projectBackendApi.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoViewModel (private val todoRepository: TodoRepository) : ParentViewModel() {

    sealed class State {     // to restrict states's inheritance
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val todos: List<Todo>?) : State()
        data class Error(val error: String?) : State()
        data class IntError(val error: Int) : State()
    }

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData
    //get todos from api, initial
    fun getTodosFromBack() {
        _liveData.value = State.ShowLoading

        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                todoRepository.getTodos()
            }
            _liveData.postValue(
                State.Result(result)
            )
        }
        _liveData.value =
            State.HideLoading

    }

    override fun handleError(e: Throwable) {
        _liveData.value = State.HideLoading
        if (e is ConnectionFailedException) {
            _liveData.value = State.IntError(e.messageInt)
        } else {
            _liveData.value = State.Error(e.localizedMessage)
        }
    }
}