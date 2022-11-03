package com.devopworld.tasktracker.ui.customui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.devopworld.tasktracker.data.component_model.DialogArguments

@Composable
fun CustomDialog(
    arguments: DialogArguments,
    isDialogOpen: Boolean,
    onDismissRequest: () -> Unit
){
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = arguments.title) },
            text = { Text(text = arguments.text) },
            confirmButton = {
                Button(onClick = arguments.onConfirmAction) {
                    Text(text = arguments.confirmText)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismissRequest) {
                    Text(text = arguments.dismissText)
                }
            }
        )
    }
}