package com.devopworld.tasktracker

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.devopworld.tasktracker.di.localModule
import com.devopworld.tasktracker.module.AlarmModule
import com.devopworld.tasktracker.module.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                AppModule +
                        AlarmModule +
                        localModule
            )
        }
    }
}