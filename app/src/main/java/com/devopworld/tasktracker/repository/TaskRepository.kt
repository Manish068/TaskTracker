package com.devopworld.tasktracker.repository

import com.devopworld.tasktracker.data.database.TaskDao
import com.devopworld.tasktracker.data.model.TaskData
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val getAllTaskData: Flow<List<TaskData>> = taskDao.getAllTasks()

    suspend fun addTask(taskData:TaskData){
        return taskDao.insertTask(task = taskData)
    }

    fun getSelectedTask(taskId:Int): Flow<TaskData> {
        return taskDao.getSelectedTask(taskId)
    }

    suspend fun updateTask(taskData: TaskData){
        return taskDao.updateTask(taskData)
    }

    suspend fun deleteTask(taskData: TaskData){
        return taskDao.deleteTask(taskData)
    }

}