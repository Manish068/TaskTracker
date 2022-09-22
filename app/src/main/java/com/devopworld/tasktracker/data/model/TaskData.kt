package com.devopworld.tasktracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.devopworld.tasktracker.util.Constant.DATABASE_TABLE_NAME
import com.devopworld.tasktracker.util.Converters
import java.sql.Date
import java.sql.Timestamp

@Entity(tableName = DATABASE_TABLE_NAME)
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val taskId: Int = 0,
    @ColumnInfo(name = "task-title")
    val taskTitle: String,
    @ColumnInfo(name = "task-description")
    val taskDescription: String,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "create_date")
    val createdDate: Date = Date(Timestamp(System.currentTimeMillis()).time),
    @ColumnInfo(name = "start_task_time")
    val task_start_time: Long,
    @ColumnInfo(name = "end_task_time")
    val task_end_time: Long
)