package com.devopworld.tasktracker.ui.composable

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.devopworld.tasktracker.Navigation.Screen
import com.devopworld.tasktracker.screens.FabButton
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskListScreen(
    action: Action,
    mainViewModel: MainViewModel,
    userName: String,
    onTaskDetailOpen: (String,Int) -> Unit
) {

    LaunchedEffect(key1 = action) {
        mainViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by mainViewModel.allTask.collectAsState()


    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = {
            mainViewModel.action.value = it
                     },
        cannTitle = mainViewModel.taskTitle.value,
        action = action,
        onUndoClicked = {
            mainViewModel.action.value = it
        }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskScreenAppBar(name = userName)
        },
        content = {
            TaskListScreenContent(
                allTasks = allTasks,
                mainViewModel = mainViewModel,
                onClickItem = { taskId->
                    onTaskDetailOpen(userName,taskId)
                }
            )
        },
        floatingActionButton = {
            FabButton(Icons.Filled.Add, nextButtonClick = {
                onTaskDetailOpen(userName,-1)
            })
        }
    )
}


@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onComplete: (Action) -> Unit,
    cannTitle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(
                        action = action,
                        cannedTitle = cannTitle
                    ),
                    actionLabel = getActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }

}

private fun setMessage(action: Action, cannedTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Task Removed."
        else -> "${action.name}: $cannedTitle"
    }
}


private fun getActionLabel(action: Action): String {
    return if (action == Action.DELETE) {
        "UNDO"
    } else {
        "OK"
    }
}

fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}

