package com.devopworld.tasktracker.ui.composable

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
import androidx.navigation.NavController
import com.devopworld.tasktracker.Navigation.Screen
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.ui.customui.TimePickerDialog
import com.devopworld.tasktracker.ui.theme.PrimaryTextColor
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.mainBgColor
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.viewmodel.MainViewModel

@Composable
fun TaskCreationScreen(
    selectedTask: TaskData?,
    mainViewModel: MainViewModel,
    navController: NavController,
    userName: String
) {
    val title: String by mainViewModel.taskTitle
    val body: String by mainViewModel.taskBody
    val taskStartTime: Long by mainViewModel.taskStartTime
    val taskEndTime: Long by mainViewModel.taskEndTime

    val context = LocalContext.current

    BackHandler {
        navController.popBackStack()
        navController.navigate(Screen.TaskScreen.withArgs(userName, Action.NO_ACTION))
    }

    Scaffold(
        topBar = {
            TaskCreationAppBar(selectedTask, navigateToListScreen = {action ->
                navController.navigate(Screen.TaskScreen.withArgs(userName, action))
            })
        },
        content = {
            TaskCreateContent(
                taskTitle = title,
                description = body,
                taskStartTime = CommonFunction.fromTimestamp(taskStartTime)!!,
                taskEndTime = CommonFunction.fromTimestamp(taskEndTime)!!,
                onTaskTitleChange = { title -> mainViewModel.taskTitle.value = title },
                onTaskDescriptionChange = { description ->
                    mainViewModel.taskBody.value = description
                },
                onStartTimeChange = { startTime ->
                    val dateTime = "${CommonFunction.TodayDate()} ${startTime}"
                    mainViewModel.taskStartTime.value = CommonFunction.dateToTimestamp(dateTime)!!
                },
                onEndTimeChange = { endTime ->
                    val dateTime = "${CommonFunction.TodayDate()} ${endTime}"
                    mainViewModel.taskEndTime.value = CommonFunction.dateToTimestamp(dateTime)!!
                },
                mainViewModel = mainViewModel,
                onCreateTaskClick = {
                    if (selectedTask == null) {
                        navController.navigate(Screen.TaskScreen.withArgs(userName, Action.ADD))
                    } else {
                        navController.navigate(Screen.TaskScreen.withArgs(userName, Action.UPDATE))
                    }
                },
                selectedTask = selectedTask
            )
        }
    )

}

private const val TAG = "TaskCreationScreen"

@Composable
fun TaskCreateContent(
    onTaskTitleChange: (String) -> Unit,
    onTaskDescriptionChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    description: String,
    taskTitle: String,
    mainViewModel: MainViewModel?,
    onCreateTaskClick: () -> Unit,
    selectedTask: TaskData?,
    taskEndTime: String,
    taskStartTime: String
) {


    val buttonText = if (selectedTask == null) "Create task" else "Update task"



    var visible by remember { mutableStateOf(false) }
    var fromStart by remember { mutableStateOf(false) }
    var fromEnd by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                modifier = Modifier.fillMaxSize()
            ) {


                Text(
                    text = stringResource(id = R.string.task_title), style = TextStyle(
                        fontFamily = fontTypography,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = taskTitle,
                    onValueChange = { title ->
                        onTaskTitleChange(title)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.task_body),
                    style = TextStyle(
                        fontFamily = fontTypography,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { description ->
                        onTaskDescriptionChange(description)
                    },
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
                                onStartTimeChange(taskStartTime)
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
                                onEndTimeChange(taskEndTime)
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
//
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

                onCreateTaskClick()

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


@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
fun previewTaskCreateContent() {

    TaskCreateContent(
        onTaskTitleChange = {},
        onTaskDescriptionChange = {},
        description = "",
        taskTitle = "",
        mainViewModel = null,
        onCreateTaskClick = {},
        selectedTask = null,
        taskEndTime = "",
        taskStartTime = "",
        onStartTimeChange = {},
        onEndTimeChange = {}
    )
}
