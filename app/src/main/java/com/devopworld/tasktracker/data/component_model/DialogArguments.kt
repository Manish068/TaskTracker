package com.devopworld.tasktracker.data.component_model

/**
 * Arguments to be used with [CustomDialog].
 *
 * @property title the dialog title
 * @property text the dialog content text
 * @property confirmText the text to be used in the confirm button
 * @property dismissText the text to be used in the dismiss button
 * @property onConfirmAction the action to be executed when the user confirms the dialog
 */

data class DialogArguments(
    val title: String,
    val text: String,
    val confirmText: String,
    val dismissText: String,
    val onConfirmAction: () -> Unit
)
