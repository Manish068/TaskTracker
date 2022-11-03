@file:JvmName("extension-context")

package com.devopworld.tasktracker.Extension

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.app.AlarmManagerCompat
import androidx.core.net.toUri
import java.util.Calendar

private const val InvalidVersion = "x.x.x"

/**
 * Sets a alarm using [AlarmManagerCompat] to be triggered based on the given parameter.
 *
 * **This function can only be called if the permission `SCHEDULE_EXACT_ALARM` is granted to
 * the application.**
 *
 * @see [android.Manifest.permission.SCHEDULE_EXACT_ALARM]
 *
 * @param triggerAtMillis time in milliseconds that the alarm should go off, using the
 * appropriate clock (depending on the alarm type).
 * @param operation action to perform when the alarm goes off
 * @param type type to define how the alarm will behave
 */
private const val TAG = "ContextExtensions"
fun Context.setExactAlarm(
    triggerAtMillis: Long,
    operation: PendingIntent?,
    type: Int = AlarmManager.RTC_WAKEUP
) {
    val currentTime = Calendar.getInstance().timeInMillis
    if (triggerAtMillis <= currentTime) {
        Log.d(TAG, "It is not possible to set alarm in the past $triggerAtMillis and $currentTime")
        return
    }

    if (operation == null) {
        Log.d(TAG, "PendingIntent is null")
        return
    }

    val manager = getAlarmManager()
    manager?.let {
        AlarmManagerCompat.setExactAndAllowWhileIdle(it, type, triggerAtMillis, operation)
        Log.d(TAG, "setExactAlarm: ${it.nextAlarmClock} $type $triggerAtMillis $operation")
    }
}

/**
 * Cancels a alarm set on [AlarmManager], based on the given [PendingIntent].
 *
 * @param operation action to be canceled
 */
fun Context.cancelAlarm(operation: PendingIntent?) {
    if (operation == null) {
        Log.d(TAG, "PendingIntent is null")
        return
    }

    val manager = getAlarmManager()
    manager?.let { manager.cancel(operation) }
}

/**
 * Gets string from color in format "#XXXXXX".
 *
 * @param colorRes the color resource id
 *
 * @return string from color in format "#XXXXXX"
 */
@SuppressLint("ResourceType")
fun Context.getStringColor(@ColorRes colorRes: Int): String =
    resources.getString(colorRes)

/**
 * Opens the given url in string format.
 *
 * @param url the url in string format
 */
fun Context.openUrl(url: String) {
    with(Intent(Intent.ACTION_VIEW)) {
        this.data = url.toUri()
        startActivity(this)
    }
}

/**
 * Returns the version name of the application.
 *
 * @return the version name of the application.
 */
@Suppress("Deprecation") // SDK below Tiramisu needs to access the deprecated version
fun Context.getVersionName(): String {
    var packageInfo: PackageInfo? = null
    packageName.let {
        try {
            packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(it, PackageManager.PackageInfoFlags.of(0))
            } else {
                packageManager.getPackageInfo(it, 0)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "getVersionName:",e )
        }
    }
    return packageInfo?.versionName ?: InvalidVersion
}
