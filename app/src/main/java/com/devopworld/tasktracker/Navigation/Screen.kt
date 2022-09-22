package com.devopworld.tasktracker.Navigation

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash_screen")
    object TaskScreen:Screen("task_screen")
    object CreateTaskScreen:Screen("create_task_screen")

    fun withArgs(vararg  args:Any):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}
