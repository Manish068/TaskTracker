package com.devopworld.tasktracker.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.repository.TaskRepository
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(private val taskRepository: TaskRepository) : ViewModel() {
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
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
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

    private fun deleteTask(){
        viewModelScope.launch {
            val task = TaskData(
                taskId =taskId.value,
                taskTitle = taskTitle.value,
                taskDescription = taskBody.value,
                task_start_time = taskStartTime.value,
                task_end_time = taskEndTime.value
            )

            taskRepository.deleteTask(task)
        }
    }

   private fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val task = TaskData(
                taskId =taskId.value,
                taskTitle = taskTitle.value,
                taskDescription = taskBody.value,
                task_start_time = taskStartTime.value,
                task_end_time = taskEndTime.value
            )
            taskRepository.updateTask(task)
        }
    }

    fun changeMutableValueOfTask(selectedTask: TaskData?) {
        if (selectedTask != null) {
            taskId.value = selectedTask.taskId
            taskTitle.value = selectedTask.taskTitle
            taskBody.value = selectedTask.taskDescription
            taskStartTime.value = selectedTask.task_start_time
            taskEndTime.value = selectedTask.task_end_time
        } else {
            taskId.value = 0
            taskTitle.value = ""
            taskBody.value = ""
            taskStartTime.value = 0
            taskEndTime.value = 0
        }
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


    private val _selectedTask = MutableStateFlow<TaskData?>(null)
    val selectedTask: StateFlow<TaskData?> = _selectedTask


    fun getSelected(taskId: Int) {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.getSelectedTask(taskId).collect {
                        _selectedTask.value = it
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "getSelected: ", e)
            }

    }

    fun ModifyData(list:List<TaskData>): List<TaskData> {
        val map = mutableMapOf<Long,TaskData>()
        val incompleteList = mutableListOf<Long>()
        val completeList = mutableListOf<Long>()
        list.sortedBy {
            it.task_start_time
        }.forEach {
            if(it.status =="INCOMPLETE"){
                incompleteList.add(it.task_start_time)
                incompleteList.sort()
                map[it.task_start_time] = it
            }else{
                completeList.add(it.task_start_time)
                map[it.task_start_time]=it
            }
        }

        val value = map.toList().sortedByDescending { it.second.status.length }
        val taskData = mutableListOf<TaskData>()
        value.forEach {
            taskData.add(it.second)
        }
        return taskData
    }

    fun updateTaskCompleted(taskData: TaskData){
        viewModelScope.launch {
            taskRepository.updateTask(taskData)
        }
    }
}