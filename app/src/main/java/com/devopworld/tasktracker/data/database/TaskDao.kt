package com.devopworld.tasktracker.data.database

import androidx.room.*
import com.devopworld.tasktracker.data.model.TaskData
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskData)

    @Query("Select * from task_table")
    fun getAllTasks(): Flow<List<TaskData>>

    @Query("Select * from task_table where id = :taskId")
    fun getSelectedTask(taskId:Int):Flow<TaskData>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task:TaskData)

    @Delete
    suspend fun deleteTask(task: TaskData)

}
