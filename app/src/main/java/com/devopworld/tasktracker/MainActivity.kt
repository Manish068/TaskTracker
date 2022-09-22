package com.devopworld.tasktracker

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devopworld.tasktracker.Navigation.NavigationHost
import com.devopworld.tasktracker.ui.theme.TaskTrackerTheme
import com.devopworld.tasktracker.util.MainEvent
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController:NavHostController
    private val viewModel by viewModels<PreferenceViewModel>()
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            TaskTrackerTheme {
                // A surface container using the 'background' color from the theme
//                FirstScreen()
                navController = rememberNavController()

                NavigationHost(navController,viewModel,mainViewModel)

            }
        }
    }

    private fun getValueFromKey(key: String) {
        lifecycleScope.launchWhenResumed {
            viewModel.getValueFromKey(key).collect{
                updateViewOnEvent(it)
            }
        }
    }

    private fun setValueForKey(key :String, value: String) {
        lifecycleScope.launchWhenResumed {
            viewModel.storeValueForKey(key,value)
                .collect{
                    updateViewOnEvent(it)
                }
        }
    }

    private val TAG = "MainActivity"
    private fun updateViewOnEvent(
        event: MainEvent
    ) {
        when (event) {
            is MainEvent.NamedCachedSuccess -> {
                // desired logic goes here
                Log.d(TAG, " value stored")
            }
            is MainEvent.CachedNameFetchSuccess -> {
                val name = event.name
                Log.d(TAG, "Values: $name")
                // desired logic goes here
            }
        }
    }

    private fun cacheName(
        name: String
    ) {
        lifecycleScope.launchWhenResumed {
            viewModel.cacheName(
                name = name
            ).collect { event ->
                updateViewOnEvent(event)
            }
        }
    }

    private fun getCachedName() {
        lifecycleScope.launchWhenResumed {
            viewModel.getCachedName().collect { event ->
                updateViewOnEvent(event)
            }
        }
    }



}

