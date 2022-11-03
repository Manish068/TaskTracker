package com.devopworld.tasktracker.Alarm

import android.util.Log
import com.devopworld.tasktracker.Notification.NotificationInteractor
import com.devopworld.tasktracker.repository.TaskRepository
import com.devopworld.tasktracker.util.TASKSTATUS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "ShowAlarm"

class ShowAlarm(
    private val taskRepository: TaskRepository,
    private val notificationInteract: NotificationInteractor
) : KoinComponent {

    private val coroutineScope: CoroutineScope by inject()

    suspend operator fun invoke(taskId: Int) {
        coroutineScope.launch {
            taskRepository.getSelectedTask(taskId).collect {
                val task = it
                if (task.status == TASKSTATUS.COMPLETE.toString()) {
                    Log.d(TAG, "Task is already completed. Will not notify ")
                    return@collect
                } else {
                    Log.d(TAG, "Notifying")
                    notificationInteract.show(task)
                }
            }
        }
    }
}