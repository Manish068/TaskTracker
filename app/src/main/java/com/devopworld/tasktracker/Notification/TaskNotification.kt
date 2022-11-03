package com.devopworld.tasktracker.Notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.devopworld.tasktracker.Extension.getNotificationManager
import com.devopworld.tasktracker.Navigation.DestinationDeepLink
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.permission.AlarmPermission
import com.devopworld.tasktracker.util.Constant
import com.devopworld.tasktracker.util.PreferenceConstant.USER_NAME
import com.devopworld.tasktracker.viewmodel.DataStorePreference
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "TaskNotification"
class TaskNotification(
    private val context:Context,
    private val taskNotificationChannel: TaskNotificationChannel,
    private val alarmPermission: AlarmPermission,
    private val dataStorePreference: DataStorePreference
):KoinComponent {
    private val coroutineScope: CoroutineScope by inject()
    var name:String?=null
    init {
        coroutineScope.launch {
            dataStorePreference.getValueFromKey(USER_NAME).collect{
                name=it.name
            }
        }
    }

    fun show(task:TaskData){
        Log.d(TAG, "Showing notification for '${task.taskTitle}'")
        val builder = buildNotification(task)
//        builder.addAction(getCompleteAction(task))

        if (!alarmPermission.hasNotificationPermission()) {
            Log.d(TAG, "Permission not granted - ignoring alarm")
            return
        }
        context.getNotificationManager()?.notify(task.taskId, builder.build())
    }

    private fun buildNotification(task: TaskData) = NotificationCompat.Builder(context,taskNotificationChannel.getChannelId()).apply {
        setSmallIcon(com.google.android.material.R.drawable.ic_clock_black_24dp)
        setContentTitle(context.getString(R.string.app_name))
        setContentText(task.taskTitle)
        setContentIntent(buildPendingIntent(task))
        setAutoCancel(true)
//        addAction(getSnoozeAction(task))
    }

    private fun buildPendingIntent(task: TaskData): PendingIntent{

        var openTaskIntent:Intent?=null
        if(name!=null){
             openTaskIntent = Intent(Intent.ACTION_VIEW,
                DestinationDeepLink.getTaskDetailUri(task.taskId, name!!)
            )
        }else{
            Log.e(TAG, "buildPendingIntent: Name is null" )
        }

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(openTaskIntent)
            getPendingIntent(
                REQUEST_CODE_OPEN_TASK,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
    companion object {

        private const val REQUEST_CODE_OPEN_TASK = 1_121_111

        private const val REQUEST_CODE_ACTION_COMPLETE = 1_234

        private const val REQUEST_CODE_ACTION_SNOOZE = 4_321

        private const val ACTION_NO_ICON = 0
    }
}