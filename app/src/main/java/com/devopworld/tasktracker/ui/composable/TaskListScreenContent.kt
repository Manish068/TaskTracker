package com.devopworld.tasktracker.ui.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.ui.theme.boardBackground
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.mainBgColor
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.util.RequestState
import com.devopworld.tasktracker.viewmodel.MainViewModel
import java.sql.Date
import java.sql.Timestamp

private const val TAG = "TaskListScreenContent"

@Composable
fun TaskListScreenContent(mainViewModel: MainViewModel, allTasks: RequestState<List<TaskData>>) {
    if (allTasks is RequestState.Success) {
        HandleListContent(
            allTasks.data,
            mainViewModel
        )
    }
}

@Composable
fun HandleListContent(data: List<TaskData>, mainViewModel: MainViewModel) {
    if (data.isEmpty()) {
        emptyScreen()
    } else {
        DisplayListContent(data, mainViewModel)
    }
}


@Composable
fun DisplayListContent(data: List<TaskData>, mainViewModel: MainViewModel) {
    val selectedIndex by remember { mutableStateOf(-1) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn {
            items(
                data,
                key = { task ->
                    task.taskId
                }
            ) { task ->

                TaskItem(
                    task,
                    selectedIndex,
                    mainViewModel
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: TaskData,
    selectedIndex: Int,
    mainViewModel: MainViewModel?
) {
    Log.d(TAG, "TaskItem Title: ${task.taskTitle}")
    Log.d(TAG, "TaskItem Description: ${task.taskDescription}")
    Log.d(TAG, "TaskItem Created Date: ${task.createdDate}")
    Log.d(TAG, "TaskItem Start Time: ${task.task_start_time}")
    Log.d(TAG, "TaskItem End Time: ${task.task_end_time}")

    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(boardBackground.copy(0.5f), shape = RoundedCornerShape(10.dp))
            .fillMaxWidth().padding(10.dp)

        ,
    ) {
        Column() {
            Text(text = task.taskTitle,)
            Text(text = task.taskDescription)
            Text(text = task.createdDate.toString())
            Text(text = CommonFunction.fromTimestamp(task.task_start_time).toString())
            Text(text = CommonFunction.fromTimestamp(task.task_end_time).toString())
        }
    }
}

@Composable
fun emptyScreen() {
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
fun previewTaskItem() {
    TaskItem(
        task = TaskData(
            1,
            "Test 1",
            "Lorem ipsum dolor sit amet," +
                    " consectetur adipiscing elit. Sed odio libero," +
                    " semper at lectus non, luctus pellentesque magna.",
            Date(Timestamp(System.currentTimeMillis()).time),
            1663856100000,1663859700000
        ), selectedIndex =0, mainViewModel = null
    )
}



