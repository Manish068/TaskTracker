package com.devopworld.tasktracker.Alarm

import com.devopworld.tasktracker.Notification.TaskNotificationScheduler

internal class ScheduleAlarmImpl(
    private val taskNotificationScheduler: TaskNotificationScheduler
) : ScheduleAlarm {
    override suspend fun schedule(taskId: Int, timeInMillis: Long) {
        taskNotificationScheduler.scheduleTaskAlarm(taskId,timeInMillis)
    }

}