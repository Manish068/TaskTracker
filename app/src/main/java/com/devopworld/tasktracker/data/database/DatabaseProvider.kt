package com.devopworld.tasktracker.data.database

import android.content.Context
import androidx.room.Room
import com.devopworld.tasktracker.util.Constant
import kotlinx.coroutines.CoroutineScope

class DatabaseProvider(
    private val context: Context
) {
    private var database: TaskDatabase? = null

    /**
     * Gets an instance of [TaskDatabase].
     *
     * @return an instance of [TaskDatabase]
     */
    fun getInstance(): TaskDatabase =
        database ?: synchronized(this) {
            database ?: buildDatabase().also { database = it }
        }

    private fun buildDatabase(): TaskDatabase =
        Room.databaseBuilder(
            context, TaskDatabase::class
                .java, Constant.DATABASE_NAME
        ).build()

}