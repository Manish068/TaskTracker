package com.devopworld.tasktracker.data.model

internal data class TaskDetailActions(
    val onTitleChange: (String) -> Unit = {},
    val onDescriptionChange: (String) -> Unit = {},
    val onStartTimeChange: (String) -> Unit,
    val onEndTimeChange: (String) -> Unit,
    val onCreateTaskClick: ()->Unit
    )