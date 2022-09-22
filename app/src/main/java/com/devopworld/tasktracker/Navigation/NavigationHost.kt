package com.devopworld.tasktracker.Navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devopworld.tasktracker.screens.MainScreen
import com.devopworld.tasktracker.screens.StartScreen
import com.devopworld.tasktracker.util.PreferenceConstant.USER_NAME
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import kotlinx.coroutines.runBlocking


private const val TAG = "NavigationHost"

@ExperimentalAnimationApi
@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: PreferenceViewModel,
    mainViewModel: MainViewModel
) {


    var nameSaved: Boolean = false
    var userName: String = ""


    runBlocking {
        viewModel.getValueFromKey(USER_NAME).collect {
            nameSaved = it.name.isNotBlank() && it.name.isNotEmpty()
            if (nameSaved) {
                userName = it.name
            }
            Log.d(TAG, "Named is Saved: ${it.name}")
        }
    }


    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {


        StartScreen(navController = navController, viewModel = viewModel, nameSaved, userName)

        MainScreen(navController, mainViewModel)

        CreateTaskScreen(navController,mainViewModel)

    }
}