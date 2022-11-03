package com.devopworld.tasktracker.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.devopworld.tasktracker.data.dataStore

class UserPreferenceProvider (private val context: Context){

    fun provideUserDataStorePreferences(): DataStore<Preferences> {
        return context.dataStore
    }
}