package com.devopworld.tasktracker.viewmodel

import com.devopworld.tasktracker.repository.UserPreferencesRepository
import com.devopworld.tasktracker.util.MainEvent
import kotlinx.coroutines.flow.flow

class DataStorePreferenceImpl(
    private val userPreferencesRepository: UserPreferencesRepository
) : DataStorePreference() {

    override fun cacheName(name: String) = flow {
        userPreferencesRepository.setName(name)
        emit(MainEvent.NamedCachedSuccess)
    }

    override fun getCacheName() = flow {
        val result = userPreferencesRepository.getName()
        val name = result.getOrNull().orEmpty() // don't care if it failed right now but you might
        emit(MainEvent.CachedNameFetchSuccess(name))
    }

    override fun storeValueForKey(key: String, value: String) =
        flow {
            userPreferencesRepository.setKeyAndValue(key, value)
            emit(MainEvent.NamedCachedSuccess)
        }

    override fun getValueFromKey(key: String) = flow {
        val result = userPreferencesRepository.getValueFromKey(key)
        val value = result.getOrNull().orEmpty()
        emit(MainEvent.CachedNameFetchSuccess(value))
    }

}