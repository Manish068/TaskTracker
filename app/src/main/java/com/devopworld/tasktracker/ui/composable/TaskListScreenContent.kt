package com.devopworld.tasktracker.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.ui.customui.Timer
import com.devopworld.tasktracker.ui.theme.boardBackground
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.mainBgColor
import com.devopworld.tasktracker.util.RequestState
import com.devopworld.tasktracker.util.TASKSTATUS
import com.devopworld.tasktracker.viewmodel.AlarmViewModel
import com.devopworld.tasktracker.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel
import java.sql.Date
import java.sql.Timestamp


@Composable
fun TaskListScreenContent(
    mainViewModel: MainViewModel,
    allTasks: RequestState<List<TaskData>>,
    onClickItem: (Int) -> Unit
) {
    if (allTasks is RequestState.Success) {
        HandleListContent(
            allTasks.data,
            mainViewModel,
            onClickItem
        )
    }
}

@Composable
fun HandleListContent(
    data: List<TaskData>,
    mainViewModel: MainViewModel,
    onClickItem: (Int) -> Unit
) {
    if (data.isEmpty()) {
        EmptyScreen()
    } else {
        val modifiedData = mainViewModel.ModifyData(data)
        DisplayListContent(modifiedData, mainViewModel, onClickItem)
    }
}


@Composable
fun DisplayListContent(
    data: List<TaskData>,
    mainViewModel: MainViewModel,
    onClickItem: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn {
            itemsIndexed(
                data,
            ) { index, task ->

                TaskItem(
                    index,
                    task,
                    mainViewModel,
                    onClickItem
                )


            }
        }
    }
}


@Composable
fun TaskItem(
    index: Int,
    task: TaskData,
    mainViewModel: MainViewModel?,
    onClickItem: (Int) -> Unit,
    alarmViewModel: AlarmViewModel = getViewModel()
) {

    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(boardBackground.copy(0.5f), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClickItem(task.taskId)
            },
    ) {
        Column() {
            Text(
                text = task.taskTitle, style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = task.taskDescription, style = TextStyle(
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
        }
        val firstTask = index == 0 && task.status == TASKSTATUS.INCOMPLETE.toString()
        if (firstTask) {
            alarmViewModel.setAlarm(task.taskId, task.task_end_time)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Timer(
                    index,
                    taskData = task,
                    handleColor = Color.Green,
                    inActiveColor = Color.DarkGray,
                    activeBarColor = Color.Green,
                    onCompletedTask = {
                        task.status = TASKSTATUS.COMPLETE.toString()
                        mainViewModel!!.updateTaskCompleted(task)
                    }
                )
            }
        }
    }
}


@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.mainBgColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.empty_list),
            contentDescription = stringResource(id = R.string.empty_task),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(id = R.string.no_task),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = fontTypography,
                fontSize = 25.sp,
            )
        )
    }
}

//@Preview(
//    showBackground = true
//)
//@Composable
//fun previewEmptyContent() {
//    emptyScreen()
//}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_6
)
@Composable
fun PreviewTaskItem() {
    TaskItem(
        0,
        task = TaskData(
            1,
            "Test 1",
            "Lorem ipsum dolor sit amet," +
                    " consectetur adipiscing elit. Sed odio libero," +
                    " semper at lectus non, luctus pellentesque magna.",
            Date(Timestamp(System.currentTimeMillis()).time),
            System.currentTimeMillis(), System.currentTimeMillis()
        ),
        mainViewModel = null,
        onClickItem = {

        }
    )
}


