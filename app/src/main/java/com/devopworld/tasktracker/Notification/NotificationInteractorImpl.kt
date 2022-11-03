package com.devopworld.tasktracker.Notification

import android.util.Log
import com.devopworld.tasktracker.data.model.TaskData

private const val TAG = "NotificationInteractorI"

class NotificationInteractorImpl(
    private val taskNotification: TaskNotification
) : NotificationInteractor {
    override fun show(task: TaskData) {
        Log.d(TAG, "show: - alarmId = ${task.taskId}")
        taskNotification.show(task)
    }
}