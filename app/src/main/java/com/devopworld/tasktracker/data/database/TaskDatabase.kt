package com.devopworld.tasktracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.util.Converters

@Database(entities = [TaskData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}