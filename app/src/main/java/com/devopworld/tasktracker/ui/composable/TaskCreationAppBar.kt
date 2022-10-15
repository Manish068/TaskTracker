package com.devopworld.tasktracker.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.util.Action

@Composable
fun TaskCreationAppBar(selectedTask: TaskData?, navigateToListScreen:(Action)->Unit) {

    if(selectedTask==null){
        NewTaskCreationTaskBar(navigateToListScreen)
    }else{
        ExistingTaskAppBar(navigateToListScreen)
    }

}

@Composable
fun NewTaskCreationTaskBar(
    navigateToListScreen:(Action)->Unit
) {
    TopAppBar(
        title = {
            Text(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.new_task),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close) )
            }
        }
    )
}

@Composable
fun ExistingTaskAppBar(navigateToListScreen: (Action) -> Unit) {
    TopAppBar(
        title = {
            Text(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.new_task),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            DeleteAction(navigateToListScreen)
            CloseAction(navigateToListScreen)

        }
    )
}

@Composable
fun CloseAction(navigateToListScreen: (Action) -> Unit) {
    IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close) )
    }
}

@Composable
fun DeleteAction(navigateToListScreen: (Action) -> Unit) {

    IconButton(onClick = { navigateToListScreen(Action.DELETE) }) {
        Icon(imageVector = Icons.Filled.Delete, contentDescription = stringResource(id = R.string.delete) )
    }
}
