package com.devopworld.tasktracker.Navigation

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devopworld.tasktracker.screens.MainScreen
import com.devopworld.tasktracker.screens.StartScreen
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.PreferenceConstant.USER_NAME
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.getViewModel


private const val TAG = "NavigationHost"

@ExperimentalAnimationApi
@Composable
fun NavigationHost(
    navController: NavHostController,
    mainViewModel: MainViewModel= getViewModel(),
    preferenceViewModel: PreferenceViewModel = getViewModel(),
) {


    var nameSaved: Boolean = false
    var userName: String = ""


    runBlocking {
        preferenceViewModel.getValueFromKey(USER_NAME).collect {
            nameSaved = it.name.isNotBlank() && it.name.isNotEmpty()
            if (nameSaved) {
                userName = it.name
            }
            Log.d(TAG, "Named is Saved: ${it.name}")
        }
    }
    val context = LocalContext.current
    val actions = remember(navController) { Actions(navController, context) }

    NavHost(navController = navController, startDestination = Destinations.SplashScreen) {

        StartScreen(
            onSplashScreenComplete = actions.homeScreen,
            viewModel = preferenceViewModel,
            nameSaved = nameSaved,
            userName = userName,
        )

        MainScreen(
            onTaskDetailOpen = actions.openTaskDetail,
            mainViewModel = mainViewModel,
        )

        CreateTaskScreen(
            mainViewModel,
            onBackButton = actions.navigateUp,
            onTaskAlterCompleted = actions.homeScreen
        )

    }
}

internal data class Actions(val navController: NavHostController, val context: Context) {

    val homeScreen: (String, Action) -> Unit = { username, action ->
        when (action) {
            Action.NO_ACTION -> {
                navController.popBackStack()
            }
            else -> {}
        }
        navController.navigate("${Destinations.Home}/$username/${action.name}")
    }

    val openTaskDetail: (String, Int) -> Unit = { username, taskId ->
        navController.navigate("${Destinations.TaskDetail}/$username/$taskId")
    }

    val openAbout: () -> Unit = {
        navController.navigate(Destinations.About)
    }

    val openTracker: () -> Unit = {
        navController.navigate(Destinations.Tracker)
    }

    val openOpenSourceLicense: () -> Unit = {
        navController.navigate(Destinations.OpenSource)
    }

    val openTaskBottomSheet: () -> Unit = {
        navController.navigate(Destinations.BottomSheet.Task)
    }

    val openCategoryBottomSheet: (Long?) -> Unit = { categoryId ->
        val id = categoryId ?: 0L
        navController.navigate("${Destinations.BottomSheet.Category}/$id")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}