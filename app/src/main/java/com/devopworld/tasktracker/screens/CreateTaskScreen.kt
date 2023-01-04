package com.devopworld.tasktracker.Navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.devopworld.tasktracker.ui.composable.TaskCreationScreen
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.viewmodel.MainViewModel

private const val TAG = "CreateTaskScreen"
fun NavGraphBuilder.CreateTaskScreen(
    mainViewModel: MainViewModel,
    onTaskAlterCompleted: (String, Action) -> Unit,
    onBackButton: () -> Unit
) {
    composable("${Destinations.TaskDetail}/{${DestinationArgs.userName}}/{${DestinationArgs.TaskId}}",
        arguments = listOf(
            navArgument(DestinationArgs.userName) {
                type = NavType.StringType },
            navArgument(DestinationArgs.TaskId) {
            type = NavType.IntType }
        ),
        deepLinks = listOf(navDeepLink {
            uriPattern=DestinationDeepLink.TaskDetailPattern
        })
    ) { entry ->
        val taskId = entry.arguments!!.getInt(DestinationArgs.TaskId)
        val userName=entry.arguments!!.getString(DestinationArgs.userName)!!

        //if user open the task Creation screen from list of task item
        //then we need to get the selected data into selected MutableFlowState
        LaunchedEffect(key1 = taskId){ mainViewModel.getSelected(taskId) }

        //here we take the selected data into collectAsState
        val selectedTask by mainViewModel.selectedTask.collectAsState()


        //and here we check if selectedTask is not null then we update the data mutable values in viewModel
        //and if selected task is null and taskid == -1 then it means user wants to create new Task so we set all the
        //mutable values to default by updating
        LaunchedEffect(key1 = selectedTask){
            if(selectedTask!=null || taskId==-1){
                mainViewModel.changeMutableValueOfTask(selectedTask)
            }
        }


        TaskCreationScreen(
            selectedTask,
            mainViewModel,
            userName,
            onBackButton =onBackButton,
            onTaskAlterCompleted =onTaskAlterCompleted
        )
    }

}