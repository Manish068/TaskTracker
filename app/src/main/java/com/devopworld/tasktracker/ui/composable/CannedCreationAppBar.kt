package com.devopworld.tasktracker.ui.composable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.util.Action

@Composable
fun CannedCreationAppBar(
    selectedCanned: TaskData?,
    navigateToCannedListScreen: (Action) -> Unit
) {
    //check if open selected canned then show existing app bar for update and delete
    //else new Canned Task Bar for creating new Cann
    if (selectedCanned == null) {
        NewCannedTaskBar(navigateToCannedListScreen = navigateToCannedListScreen)
    } else {
        ExistingCannedTaskBar(
            selectedCanned = selectedCanned,
            navigateToCannedListScreen = navigateToCannedListScreen
        )
    }
}


@Composable
fun NewCannedTaskBar(
    navigateToCannedListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Hello world")

        },
        navigationIcon = {
            BackAction(onBackClicked = navigateToCannedListScreen)
        },
        actions = {
            AddAction(navigateToCannedListScreen)
        },

        elevation = 4.dp,
    )
}


@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = ""
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "",
        )
    }
}


@Composable
@Preview
private fun previewAppBar() {
    NewCannedTaskBar(navigateToCannedListScreen = {})
}


//for Existing Canned Task bar

@Composable
fun ExistingCannedTaskBar(
    selectedCanned: TaskData,
    navigateToCannedListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
//                         CloseAction(onCloseClicked = navigateToCannedListScreen)
        },
        title = {
            Text(
                text = selectedCanned.taskTitle,

                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            ExistingTaskAppBarAction(
                selectedTask = selectedCanned,
                navigateToListScreen = navigateToCannedListScreen
            )
        }
    )
}


@Composable
fun ExistingTaskAppBarAction(
    selectedTask: TaskData,
    navigateToListScreen: (Action) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

//    DisplayAlertDialog(
//        title = stringResource(id = R.string.delete_task,selectedTask.title),
//        message = stringResource(id = R.string.delete_task_confirmation,selectedTask.title),
//        openDialog = openDialog,
//        closeDialog = {
//            openDialog = false
//        },
//        onYesClick = {
//            navigateToListScreen(Action.DELETE)
//        }
//    )
//    DeleteAction(
//        onDeleteClicked = {
//            openDialog = true
//        }
//    )
//    UpdateAction(onUpdateClicked = navigateToListScreen)

}

//@Composable
//fun CloseAction(
//    onCloseClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
//        Icon(
//            imageVector = Icons.Filled.Close,
//            contentDescription = stringResource(id = R.string.close_action),
//            tint = MaterialTheme.colors.topAppBarActionColor
//        )
//    }
//}
//
//@Composable
//fun DeleteAction(
//    onDeleteClicked: () -> Unit
//) {
//    IconButton(onClick = { onDeleteClicked() }) {
//        Icon(
//            imageVector = Icons.Filled.Delete,
//            contentDescription = stringResource(id = R.string.delete_icon),
//            tint = MaterialTheme.colors.topAppBarRemoveAction
//        )
//    }
//}
//
//
//@Composable
//fun UpdateAction(
//    onUpdateClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
//        Icon(
//            imageVector = Icons.Filled.Check,
//            contentDescription = stringResource(
//                id = R.string.update_icon
//            ),
//            tint = MaterialTheme.colors.topAppBarActionColor
//        )
//    }
//}

@Composable
@Preview
private fun previewExistingAppBar(){
//    ExistingCannedTaskBar(selectedCanned = TaskData(
//        0,
//        "Manish",
//        "SomeTask",
//    ), navigateToCannedListScreen = {})
}