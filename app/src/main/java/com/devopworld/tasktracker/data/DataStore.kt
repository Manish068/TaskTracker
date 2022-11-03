package com.devopworld.tasktracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Extension function to return a singleton of Alkaa DataStore settings.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "task_settings")
