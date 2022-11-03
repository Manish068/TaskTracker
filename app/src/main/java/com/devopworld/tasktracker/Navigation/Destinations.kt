package com.devopworld.tasktracker.Navigation

import android.net.Uri
import androidx.core.net.toUri

object Destinations {

    /**
     * Splash destination.
     */
    const val SplashScreen = "splash_screen"

    /**
     * Home destination.
     */
    const val Home = "home"

    /**
     * Task detail destination.
     */
    const val TaskDetail = "task_detail"

    /**
     * About destination.
     */
    const val About = "about"

    /**
     * Open Source Libraries destination.
     */
    const val OpenSource = "open_source"

    /**
     * Task Tracker dynamic feature destination.
     */
    const val Tracker = "tracker"

    /**
     * Represents the navigation where the target is a BottomSheet rather than a full screen.
     */
    object BottomSheet {

        /**
         * Bottom Sheet Category destination.
         */
        const val Category = "bottom_sheet_category"

        /**
         * Bottom Sheet Category destination.
         */
        const val Task = "bottom_sheet_task"
    }
}

/**
 * Represents the arguments to be passed through the [Destinations].
 */
object DestinationArgs {

    /**
     * Argument to be passed to [Destinations.Home] representing the task id to be detailed.
     */
    const val userName = "user_name"
    const val action="action"

    /**
     * Argument to be passed to [Destinations.TaskDetail] representing the task id to be detailed.
     */
    const val TaskId = "task_id"

    /**
     * Argument to be passed to [Destinations.BottomSheet.Category] representing the category id to
     * be detailed.
     */
    const val CategoryId = "category_id"
}

/**
 * Represents the Deep Links to implicit navigate through the application, like PendingIntent.
 */
object DestinationDeepLink {

    private val BaseUri = "app://com.devopworld.tasktracker".toUri()

    /**
     * Deep link pattern to be registered in [Destinations.SplashScreen] composable.
     */
    val SplashPattern = "$BaseUri/splash_screen"


    /**
     * Deep link pattern to be registered in [Destinations.Home] composable.
     */
    val HomePattern = "$BaseUri/home/{${DestinationArgs.userName}}{${DestinationArgs.action}}"

    /**
     * Deep link pattern to be registered in [Destinations.TaskDetail] composable.
     */
    val TaskDetailPattern = "$BaseUri/task_detail/{${DestinationArgs.userName}}{${DestinationArgs.TaskId}}"

    /**
     * Deep link pattern to be registered in [Destinations.BottomSheet.Category] composable.
     */
    val CategorySheetPattern =
        "$BaseUri/${DestinationArgs.CategoryId}={${DestinationArgs.CategoryId}}"

    /**
     * Returns the [Destinations.TaskDetail] deep link with the argument set.
     *
     * @return the [Destinations.TaskDetail] deep link with the argument set
     */
    fun getTaskDetailUri(taskId: Int,userName:String): Uri =
        "$BaseUri/task_detail/{${DestinationArgs.userName}=$userName}{${DestinationArgs.TaskId}=$taskId}".toUri()

    /**
     * Returns the [Destinations.Home] deep link with the argument set.
     *
     * @return the [Destinations.Home] deep link with the argument set
     */
    fun getTaskHomeUri(): Uri =
        HomePattern.toUri()
}