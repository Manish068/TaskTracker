package com.devopworld.tasktracker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.devopworld.tasktracker.Alarm.ShowAlarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmReceiver:BroadcastReceiver(),KoinComponent {


    private val coroutineScope: CoroutineScope by inject()
    private val showAlarmUseCase:ShowAlarm by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        coroutineScope.launch {
            HandleIntent(intent)
        }
    }

    suspend fun HandleIntent(intent: Intent?){
        when(intent?.action){
            ALARM_ACTION-> showAlarmUseCase(getTaskId(intent))
        }
    }

    fun getTaskId(intent: Intent):Int = intent.getIntExtra(EXTRA_TASK,0)

    companion object {

        const val EXTRA_TASK = "extra_task"

        const val ALARM_ACTION = "com.devopworld.tasktracker.SET_ALARM"

        const val COMPLETE_ACTION = "com.escodro.alkaa.SET_COMPLETE"

        const val SNOOZE_ACTION = "com.escodro.alkaa.SNOOZE"
    }

}