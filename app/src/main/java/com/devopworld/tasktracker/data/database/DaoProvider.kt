package com.devopworld.tasktracker.data.database

class DaoProvider(private val database: DatabaseProvider) {
    /**
     * Gets the [TaskDao].
     *
     * @return the [TaskDao]
     */
    fun getTaskDao(): TaskDao =
        database.getInstance().taskDao()

}