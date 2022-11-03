package com.devopworld.tasktracker.Notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.devopworld.tasktracker.Extension.setExactAlarm
import com.devopworld.tasktracker.service.AlarmReceiver

private const val TAG = "TaskNotificationSchedul"

internal class TaskNotificationScheduler(private val context: Context) {

    /**
     * Schedules a task notification based on the due date.
     *
     * @param timeInMillis the time to the alarm be scheduled
     */
    fun scheduleTaskAlarm(taskId: Int, timeInMillis: Long) {
        val receiverIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = AlarmReceiver.ALARM_ACTION
            this.putExtra(AlarmReceiver.EXTRA_TASK, taskId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId,
            receiverIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.d(TAG, "Scheduling notification for at '$timeInMillis'")
        context.setExactAlarm(timeInMillis, pendingIntent)
    }

//    /**
//     * Cancels a task notification based on the task id.
//     *
//     * @param taskId task id to be canceled
//     */
//    fun cancelTaskAlarm(taskId: Long) {
//        val receiverIntent = Intent(context, TaskReceiver::class.java)
//        receiverIntent.action = TaskReceiver.ALARM_ACTION
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            taskId.toInt(),
//            receiverIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//
//        logcat { "Canceling notification with id '$taskId'" }
//        context.cancelAlarm(pendingIntent)
//    }
}