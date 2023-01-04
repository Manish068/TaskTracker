package com.devopworld.tasktracker.screens

import android.Manifest
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.devopworld.tasktracker.Navigation.DestinationArgs
import com.devopworld.tasktracker.Navigation.DestinationDeepLink
import com.devopworld.tasktracker.Navigation.Destinations
import com.devopworld.tasktracker.data.model.AlarmPermissionActions
import com.devopworld.tasktracker.permission.AlarmPermission
import com.devopworld.tasktracker.ui.composable.TaskListScreen
import com.devopworld.tasktracker.ui.customui.AlarmPermissionDialog
import com.devopworld.tasktracker.ui.customui.NotificationPermissionDialog
import com.devopworld.tasktracker.ui.customui.RationalePermissionDialog
import com.devopworld.tasktracker.util.Action.NO_ACTION
import com.devopworld.tasktracker.util.rememberAlarmSelectionState
import com.devopworld.tasktracker.util.toAction
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.escodro.task.permission.PermissionStateFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.koin.androidx.compose.get

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalAnimationApi
fun NavGraphBuilder.MainScreen(
    mainViewModel: MainViewModel,
    onTaskDetailOpen: (String,Int) -> Unit,
) {

    composable(route = "${Destinations.Home}/{${DestinationArgs.userName}}/{${DestinationArgs.action}}",
        arguments = listOf(navArgument(DestinationArgs.userName) {
            type = NavType.StringType
            nullable = false
        }, navArgument(DestinationArgs.action) {
            type = NavType.StringType
        }),
        deepLinks = listOf(navDeepLink {
            uriPattern=DestinationDeepLink.HomePattern
        })
    ) { entry ->

        val userName = entry.arguments?.getString(DestinationArgs.userName) ?: ""
        val action = entry.arguments?.getString(DestinationArgs.action).toAction()
        val alarmPermission: AlarmPermission = get()

        val permissionAction = AlarmPermissionActions(
             hasAlarmPermission = {
                 alarmPermission.hasExactAlarmPermission()
             },
            shouldCheckNotificationPermission = alarmPermission.shouldCheckNotificationPermission()
        )

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

        val context = LocalContext.current
        val permissionState = if (permissionAction.shouldCheckNotificationPermission) {
            rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        } else {
            PermissionStateFactory.getGrantedPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
        }
        val state = rememberAlarmSelectionState()

        when {
            permissionAction.hasAlarmPermission() && permissionState.status.isGranted->{
                TaskListScreen(
                    action = databaseAction,
                    mainViewModel,
                    userName,
                    onTaskDetailOpen=onTaskDetailOpen
                )
            }
            permissionState.status.shouldShowRationale ->
                state.showRationaleDialog = true
            else -> {
                state.showExactAlarmDialog = !permissionAction.hasAlarmPermission()
                state.showNotificationDialog = !permissionState.status.isGranted
            }
        }


        AlarmPermissionDialog(
            context = context,
            isDialogOpen = state.showExactAlarmDialog,
            onCloseDialog = {
                state.showExactAlarmDialog = false
            },
        )

        RationalePermissionDialog(
            context = context,
            isDialogOpen = state.showRationaleDialog,
            onCloseDialog = {
                state.showRationaleDialog = false
            },
        )

        NotificationPermissionDialog(
            permissionState = permissionState,
            isDialogOpen = state.showNotificationDialog,
            onCloseDialog = { state.showNotificationDialog = false }
        )

    }
}



