package com.devopworld.tasktracker.Notification

import com.devopworld.tasktracker.data.model.TaskData

interface NotificationInteractor {
    fun show(task:TaskData){

    }
}