package com.devopworld.tasktracker.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.repository.TaskRepository
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject
constructor(val taskRepository: TaskRepository) : ViewModel() {
    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val taskId: MutableState<Int> = mutableStateOf(0)
    val taskTitle: MutableState<String> = mutableStateOf("")
    val taskBody: MutableState<String> = mutableStateOf("")
    val taskStartTime: MutableState<Long> = mutableStateOf(0)
    val taskEndTime: MutableState<Long> = mutableStateOf(0)

    private val _allTasks = MutableStateFlow<RequestState<List<TaskData>>>(RequestState.Idle)
    val allTask: StateFlow<RequestState<List<TaskData>>> = _allTasks

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                taskRepository.getAllTaskData.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> AddTask()
            Action.UPDATE -> {

            }
            Action.DELETE -> {

            }
            Action.DELETE_ALL -> {
                // deleteAllTasks()
            }
            Action.UNDO -> {

            }
            Action.NO_ACTION -> {

            }
        }
    }

    fun updateTask(selectedTask:TaskData?){

    }

    private fun AddTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskData(
                taskTitle = taskTitle.value,
                taskDescription = taskBody.value,
                task_start_time = taskStartTime.value,
                task_end_time = taskEndTime.value
            )
            taskRepository.addTask(task)
        }
    }



}