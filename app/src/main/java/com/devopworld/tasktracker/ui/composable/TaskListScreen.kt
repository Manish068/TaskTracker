package com.devopworld.tasktracker.ui.composable

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.devopworld.tasktracker.Navigation.Screen
import com.devopworld.tasktracker.screens.FabButton
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.viewmodel.MainViewModel

@Composable
fun TaskListScreen(
    action: Action,
    mainViewModel: MainViewModel,
    userName: String,
    navController: NavController
) {

    LaunchedEffect(key1 = action) {
        mainViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by mainViewModel.allTask.collectAsState()



    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskScreenAppBar(name = userName)
        },
        content = {
            TaskListScreenContent(
                allTasks = allTasks,
                mainViewModel = mainViewModel
            )
        },
        floatingActionButton = {
            FabButton(Icons.Filled.Add, nextButtonClick = {
                navController.navigate(
                    Screen.CreateTaskScreen.withArgs(userName, -1)
                )
            })

        }
    )
}


