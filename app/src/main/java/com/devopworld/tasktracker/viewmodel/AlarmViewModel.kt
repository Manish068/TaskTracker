package com.devopworld.tasktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devopworld.tasktracker.Alarm.ScheduleAlarm
import kotlinx.coroutines.launch

class AlarmViewModel(
    private val scheduleAlarm: ScheduleAlarm
) : ViewModel() {
    fun setAlarm(taskId:Int,timeInLong: Long) = viewModelScope.launch {
        scheduleAlarm.schedule(taskId,timeInLong)
    }
}