package com.devopworld.tasktracker.service

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.devopworld.tasktracker.util.Constant
import java.util.*


private const val TAG = "AppService"

class AppService : Service() {
    protected val NOTIFICATION_ID = 89
    private var mCurrentService: Service? = null

    /**
     * static to avoid multiple timers to be created when the service is called several times
     */
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    var oldTime: Long = 0
    private var counter = 0


    fun getmCurrentService(): Service? {
        return mCurrentService
    }

    fun setmCurrentService(mCurrentService: Service) {
        this.mCurrentService = mCurrentService
    }

    override fun onCreate() {
        super.onCreate()
        restartForeground()
        mCurrentService = this
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d(TAG, "restarting Service !!")
        counter = 0

        // it has been killed by Android and now it is restarted. We must make sure to have reinitialised everything
        if (intent == null) {
            val bck = ProcessMainClass()
            bck.launchService(this)
        }


        // make sure you call the startForeground on onStartCommand because otherwise
        // when we hide the notification on onScreen it will nto restart in Android 6 and 7

        startTimer()

        // return start sticky so if it is killed by android, it will be restarted with Intent null
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    fun restartForeground() {
        Log.i(TAG, "restarting foreground")
        try {


            val chan = NotificationChannel(
                "Foreground Service",
                "My Foreground Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            manager.createNotificationChannel(chan)

            val notification = NotificationCompat.Builder(this, "Foreground Service")
                .setContentTitle("Foreground Service")
                .setContentText("This is foreground service")
                .setSmallIcon(R.drawable.ic_menu_today)
                .build()

            startForeground(
                NOTIFICATION_ID,
                notification
            )
            Log.i(TAG, "restarting foreground successful")
            startTimer()
        } catch (e: Exception) {
            Log.e(TAG, "Error in notification " + e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy called")
        // restart the never ending service
        val broadcastIntent = Intent(Constant.RESTART_INTENT)
        sendBroadcast(broadcastIntent)
        stoptimertask()
    }

    fun startTimer() {
        Log.i(TAG, "starting Notification service again")
        Log.i(TAG, "Starting timer")

        //set a new Timer - if one is already running, cancel it to avoid two running at the same time
        stoptimertask()
        timer = Timer()

        //initialize the TimerTask's job
        initializeTimerTask()
        Log.i(TAG, "Scheduling...")
        //schedule the timer, to wake up every 1 second
        timer!!.schedule(timerTask, 1000, 1000) //
    }

    fun initializeTimerTask() {
        Log.i(TAG, "initialising TimerTask")
        timerTask = object : TimerTask() {
            override fun run() {
                Log.i("in timer", "in timer ++++  " + counter++)
            }
        }
    }
    fun stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

}