package com.devopworld.tasktracker.repository

import com.devopworld.tasktracker.data.database.DaoProvider
import com.devopworld.tasktracker.data.database.TaskDao
import com.devopworld.tasktracker.data.model.TaskData
import kotlinx.coroutines.flow.Flow


class TaskRepository(private val daoProvider: DaoProvider) {
    private val taskDao: TaskDao = daoProvider.getTaskDao()
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