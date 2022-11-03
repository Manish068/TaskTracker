package com.devopworld.tasktracker.data.model

internal data class AlarmPermissionActions(
    val hasAlarmPermission: () -> Boolean = { false },
    val shouldCheckNotificationPermission: Boolean = false,
)