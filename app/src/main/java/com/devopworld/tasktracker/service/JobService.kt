package com.devopworld.tasktracker.service

import android.app.job.JobParameters
import android.content.IntentFilter
import android.os.Handler
import com.devopworld.tasktracker.util.Constant

class JobService : android.app.job.JobService() {
    private val TAG = JobService::class.java.simpleName
    private var restartSensorServiceReceiver: RestartServiceBroadcastReceiver? = null
    private var instance: JobService? = null
    private var jobParameters: JobParameters? = null

    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        val bck = ProcessMainClass()
        bck.launchService(this)
        registerRestarterReceiver()
        instance = this
        this.jobParameters = jobParameters

        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        TODO("Not yet implemented")
    }
    private fun registerRestarterReceiver() {

        // the context can be null if app just installed and this is called from restartsensorservice
        // https://stackoverflow.com/questions/24934260/intentreceiver-components-are-not-allowed-to-register-to-receive-intents-when
        // Final decision: in case it is called from installation of new version (i.e. from manifest, the application is
        // null. So we must use context.registerReceiver. Otherwise this will crash and we try with context.getApplicationContext
        if (this.restartSensorServiceReceiver == null){
            this.restartSensorServiceReceiver = RestartServiceBroadcastReceiver()
        } else {
            try {
                unregisterReceiver(this.restartSensorServiceReceiver)
            } catch (e: Exception) {
                // not registered
            }
        }
        // give the time to run
        Handler().postDelayed(Runnable { // we register the  receiver that will restart the background service if it is killed
            // see onDestroy of Service
            val filter = IntentFilter()
            filter.addAction(Constant.RESTART_INTENT)
            try {
                registerReceiver(this.restartSensorServiceReceiver, filter)
            } catch (e: Exception) {
                try {
                    applicationContext.registerReceiver(
                        this.restartSensorServiceReceiver,
                        filter
                    )
                } catch (ex: Exception) {
                }
            }
        }, 1000)
    }

}