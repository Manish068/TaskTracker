package com.devopworld.tasktracker.ui.composable

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.data.model.TaskDetailActions
import com.devopworld.tasktracker.permission.AlarmPermission
import com.devopworld.tasktracker.ui.customui.AlarmPermissionDialog
import com.devopworld.tasktracker.ui.customui.NotificationPermissionDialog
import com.devopworld.tasktracker.ui.customui.RationalePermissionDialog
import com.devopworld.tasktracker.ui.customui.TimePickerDialog
import com.devopworld.tasktracker.ui.theme.PrimaryTextColor
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.mainBgColor
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.util.rememberAlarmSelectionState
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.escodro.task.permission.PermissionStateFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.koin.androidx.compose.get

@Composable
fun TaskCreationScreen(
    selectedTask: TaskData?,
    mainViewModel: MainViewModel,
    userName: String,
    onTaskAlterCompleted: (String, Action) -> Unit,
    onBackButton: () -> Unit
) {
    val title: String by mainViewModel.taskTitle
    val body: String by mainViewModel.taskBody
    val taskStartTime: Long by mainViewModel.taskStartTime
    val taskEndTime: Long by mainViewModel.taskEndTime

    val context = LocalContext.current

    BackHandler {
        onBackButton()
    }

    val taskDetailActions = TaskDetailActions(
        onTitleChange = { title -> mainViewModel.taskTitle.value = title },
        onDescriptionChange = { desc -> mainViewModel.taskBody.value = desc },
        onStartTimeChange = { startTime ->
            val dateTime = "${CommonFunction.TodayDate()} ${startTime}"
            mainViewModel.taskStartTime.value = CommonFunction.dateToTimestamp(dateTime)!!
        },
        onEndTimeChange = { endTime ->
            val dateTime = "${CommonFunction.TodayDate()} ${endTime}"
            mainViewModel.taskEndTime.value = CommonFunction.dateToTimestamp(dateTime)!!
        },
        onCreateTaskClick = {
            if (selectedTask == null) {
                onTaskAlterCompleted(userName, Action.ADD)
            } else {
                onTaskAlterCompleted(userName, Action.UPDATE)
            }
        }
    )

//    AlarmSelection(
//        calendar = task.dueDate,
//        interval = task.alarmInterval,
//        onAlarmUpdate = actions.onAlarmUpdate,
//        onIntervalSelect = actions.onIntervalSelect,
//        hasAlarmPermission = actions.hasAlarmPermission,
//        shouldCheckNotificationPermission = actions.shouldCheckNotificationPermission
//    )

    Scaffold(
        topBar = {
            TaskCreationAppBar(selectedTask, navigateToListScreen = { action ->
                onTaskAlterCompleted(userName, action)
            })
        },
        content = { paddingValues ->
            TaskCreateContent(
                paddingValues = paddingValues,
                taskTitle = title,
                description = body,
                taskStartTime = CommonFunction.fromTimestamp(taskStartTime)!!,
                taskEndTime = CommonFunction.fromTimestamp(taskEndTime)!!,
                mainViewModel = mainViewModel,
                selectedTask = selectedTask,
                action = taskDetailActions
            )
        }
    )

}

private const val TAG = "TaskCreationScreen"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun TaskCreateContent(
    description: String,
    taskTitle: String,
    mainViewModel: MainViewModel?,
    selectedTask: TaskData?,
    taskEndTime: String,
    taskStartTime: String,
    action: TaskDetailActions,
    paddingValues: PaddingValues,
) {

    val buttonText = if (selectedTask == null) "Create task" else "Update task"


    var visible by remember { mutableStateOf(false) }
    var fromStart by remember { mutableStateOf(false) }
    var fromEnd by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colors.mainBgColor)
            .padding(12.dp)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colors.mainBgColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                TaskInputLayout(
                    stringResource(id = R.string.task_title),
                    value=taskTitle,
                    onValueChange = { title->
                        action.onTitleChange(title)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
                TaskInputLayout(
                    stringResource(id = R.string.task_body),
                    value=description,
                    onValueChange = { description->
                        action.onDescriptionChange(description)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Column(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .weight(0.5f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_start),
                            style = TextStyle(
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    visible = !visible
                                    fromStart = true
                                    fromEnd = false
                                },
                            enabled = false,
                            singleLine = true,
                            value = taskStartTime,
                            onValueChange = { taskStartTime ->
                                action.onStartTimeChange(taskStartTime)
                            },
                            textStyle = TextStyle(
                                color = PrimaryTextColor,
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            ),
                        )

                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(0.5f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.task_ends),
                            style = TextStyle(
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            ),
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    visible = !visible
                                    fromStart = false
                                    fromEnd = true
                                },
                            singleLine = true,
                            enabled = false,
                            value = taskEndTime,
                            onValueChange = { taskEndTime ->
                                action.onEndTimeChange(taskEndTime)
                            },
                            textStyle = TextStyle(
                                color = PrimaryTextColor,
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                        )
                    }
                }

                if (visible) {
                    if (taskStartTime.isNotEmpty() && fromEnd) {
                        TimePickerDialog(
                            mainViewModel!!,
                            fromStart,
                            fromEnd,
                            onDismiss = {
                                visible = false
                            },
                            onDateTimeSave = { timestamp, time ->
                                if (fromStart) {
                                    mainViewModel.taskStartTime.value = timestamp
                                } else {
                                    mainViewModel.taskEndTime.value = timestamp
                                }
                            }
                        )
                    } else if (fromStart) {
                        TimePickerDialog(
                            mainViewModel!!,
                            fromStart,
                            fromEnd,
                            onDismiss = {
                                visible = false
                            },
                            onDateTimeSave = { timestamp, time ->
                                if (fromStart) {
                                    mainViewModel.taskStartTime.value = timestamp
                                } else {
                                    mainViewModel.taskEndTime.value = timestamp
                                }
                            }
                        )
                    } else {
                        Toast.makeText(
                            LocalContext.current,
                            R.string.warning_select_first_start_time,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomStart),
            elevation = ButtonDefaults.elevation(),
            onClick = {
                action.onCreateTaskClick()
            },
        ) {
            Text(
                text = buttonText, style = TextStyle(
                    fontFamily = fontTypography,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )
        }
    }

}

@Composable
fun TaskInputLayout(
    stringResource: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Text(
        text = stringResource, style = TextStyle(
            fontFamily = fontTypography,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { title ->
            onValueChange(title)
        },
    )
}


@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
fun PreviewTaskCreateContent() {

    TaskCreateContent(
        description = "",
        taskTitle = "",
        mainViewModel = null,
        selectedTask = null,
        taskEndTime = "",
        taskStartTime = "",
        action = TaskDetailActions({}, {}, {}, {}, {}),
        paddingValues = PaddingValues(10.dp)
    )
}
