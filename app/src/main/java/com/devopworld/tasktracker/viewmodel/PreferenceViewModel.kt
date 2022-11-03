package com.devopworld.tasktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devopworld.tasktracker.util.MainEvent
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PreferenceViewModel(
    private val dataStorePreference: DataStorePreference
) : ViewModel() {

    fun cacheName(name: String) = dataStorePreference.cacheName(name)


    fun getCacheName() = dataStorePreference.getCacheName()

    fun storeValueForKey(key: String, value: String) = dataStorePreference.storeValueForKey(key, value)

    fun getValueFromKey(key: String) = dataStorePreference.getValueFromKey(key)
}