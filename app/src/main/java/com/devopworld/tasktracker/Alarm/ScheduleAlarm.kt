package com.devopworld.tasktracker.Alarm

interface ScheduleAlarm {
    /**
     * Schedules a new alarm.
     *
     * @param alarmId the alarm id
     * @param timeInMillis the time to the alarm be scheduled
     */
    suspend fun schedule(taskId: Int, timeInMillis: Long)

}