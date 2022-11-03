package com.devopworld.tasktracker.util

import androidx.compose.runtime.*
import java.util.*

internal class AlarmSelectionState() {

    /**
     * The Exact Alarm permission dialog visibility state.
     */
    var showExactAlarmDialog by mutableStateOf(false)

    /**
     * The Notification permission dialog visibility state.
     */
    var showNotificationDialog by mutableStateOf(false)

    /**
     * The Notification Rationale dialog visibility state.
     */
    var showRationaleDialog by mutableStateOf(false)
}

@Composable
internal fun rememberAlarmSelectionState() =
    remember { AlarmSelectionState() }
