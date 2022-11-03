package com.devopworld.tasktracker.permission

import android.os.Build

internal class AndroidVersionImpl : AndroidVersion {

    override val currentVersion: Int = Build.VERSION.SDK_INT
}