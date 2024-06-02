package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.util.buildDb // Import buildDb from util package
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    val db = buildDb(getApplication()) // Use buildDb from util package
    val todoLD = MutableLiveData<Todo>()

    fun addTodo(list: List<Todo>) {
        launch {
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }
    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectTodoById(uuid))
        }
    }
    fun update(title:String, notes:String, priority:Int, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }
    fun updateTodoStatus(id: Int, isDone: Int) {
        launch {
            db.todoDao().updateTodoStatus(id, isDone)
        }
    }




    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}
