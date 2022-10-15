package com.devopworld.tasktracker.Navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devopworld.tasktracker.ui.composable.TaskCreationScreen
import com.devopworld.tasktracker.util.Constant.TASK_ARGUMENT_KEY
import com.devopworld.tasktracker.viewmodel.MainViewModel

private const val TAG = "CreateTaskScreen"
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

        Log.d(TAG, "CreateTaskScreen: Task ID: $taskId")

        //if user open the task Creation screen from list of task item
        //then we need to get the selected data into selected MutableFlowState
        LaunchedEffect(key1 = taskId){ mainViewModel.getSelected(taskId) }

        //here we take the selected data into collectAsState
        val selectedTask by mainViewModel.selectedTask.collectAsState()

        Log.d(TAG, "CreateTaskScreen: ${selectedTask?.taskTitle}")
        Log.d(TAG, "CreateTaskScreen: ${selectedTask?.task_start_time}")
        Log.d(TAG, "CreateTaskScreen: ${selectedTask?.task_end_time}")


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
            navController,
            userName
        )
    }

}