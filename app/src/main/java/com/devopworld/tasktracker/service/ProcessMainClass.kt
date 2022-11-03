package com.devopworld.tasktracker.service

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat


class ProcessMainClass {
    private fun setServiceIntent(context: Context) {
        if (serviceIntent == null) {
            serviceIntent = Intent(context, AppService::class.java)
        }
    }

    /**
     * launching the service
     */
    fun launchService(context: Context?) {
        if (context == null) {
            return
        }
        setServiceIntent(context)
        // depending on the version of Android we eitehr launch the simple service (version<O)
        // or we start a foreground service
        ContextCompat.startForegroundService(context, serviceIntent!!)
        Log.d(TAG, "ProcessMainClass: start service go!!!!")
    }

    companion object {
        val TAG = ProcessMainClass::class.java.simpleName
        private var serviceIntent: Intent? = null
    }
}