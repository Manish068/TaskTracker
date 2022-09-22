package com.devopworld.tasktracker.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devopworld.tasktracker.Navigation.Screen
import com.devopworld.tasktracker.ui.composable.TaskListScreen
import com.devopworld.tasktracker.util.Action.NO_ACTION
import com.devopworld.tasktracker.util.toAction
import com.devopworld.tasktracker.viewmodel.MainViewModel

private const val TAG = "MainScreen"

@ExperimentalAnimationApi
fun NavGraphBuilder.MainScreen(navController: NavController, mainViewModel: MainViewModel) {

    composable(route = Screen.TaskScreen.route + "/{name}/{action}",
        arguments = listOf(navArgument("name") {
            type = NavType.StringType
            nullable = false
        }, navArgument("action") {
            type = NavType.StringType
        })
    ) { entry ->

        val userName = entry.arguments?.getString("name") ?: ""
        val action = entry.arguments?.getString("action").toAction()

        var myAction by rememberSaveable {
            mutableStateOf(NO_ACTION)
        }


        LaunchedEffect(key1 = action) {
            if (action != myAction) {
                myAction = action
                mainViewModel.action.value = action
            }
        }

        val databaseAction by mainViewModel.action


        TaskListScreen(
            action = databaseAction,
            mainViewModel,
            userName,
            navController = navController,
        )


    }
}



