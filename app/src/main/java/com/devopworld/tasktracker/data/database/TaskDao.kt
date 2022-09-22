package com.devopworld.tasktracker.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devopworld.tasktracker.data.model.TaskData
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskData)

    @Query("Select * from task_table")
    fun getAllTasks(): Flow<List<TaskData>>
}
