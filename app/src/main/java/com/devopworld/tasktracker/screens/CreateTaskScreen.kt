package com.devopworld.tasktracker.Navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devopworld.tasktracker.ui.composable.TaskCreationScreen
import com.devopworld.tasktracker.util.Constant.TASK_ARGUMENT_KEY
import com.devopworld.tasktracker.viewmodel.MainViewModel


fun NavGraphBuilder.CreateTaskScreen(navController: NavController, mainViewModel: MainViewModel) {
    composable(
        Screen.CreateTaskScreen.route + "/{name}/{${TASK_ARGUMENT_KEY}}",
        arguments = listOf(
            navArgument("name") {
                type = NavType.StringType },
            navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType }
        )
    ) { entry ->
        val taskId = entry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        val userName=entry.arguments!!.getString("name")!!

        LaunchedEffect(key1 = taskId){

        }

        TaskCreationScreen(
            mainViewModel,
            navController,
            userName
        )
    }

}