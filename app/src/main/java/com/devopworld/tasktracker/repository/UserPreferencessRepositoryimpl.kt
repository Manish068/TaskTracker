package com.devopworld.tasktracker.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

internal class UserPreferencessRepositoryimpl(
    private val userPreferenceProvider: UserPreferenceProvider
):UserPreferencesRepository{

    private val userDataStorePreferences = userPreferenceProvider.provideUserDataStorePreferences()

    override suspend fun setName(
        name: String
    ) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[KEY_NAME] = name
            }
        }
    }

    override suspend fun getName(): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to "" if not set
                    preferences[KEY_NAME]
                }
            val value = flow.firstOrNull() ?: "" // we only care about the 1st value
            value
        }
    }

    override suspend fun setKeyAndValue(key: String, value: String) {
        Result.runCatching {
            userDataStorePreferences.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun getValueFromKey(key: String): Result<String> {
        return Result.runCatching {
            val flow = userDataStorePreferences.data
                .catch { exception ->
                    /*
                     * dataStore.data throws an IOException when an error
                     * is encountered when reading data
                     */
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get our name value, defaulting to "" if not set
                    preferences[stringPreferencesKey(key)]
                }
            val value = flow.firstOrNull() ?: "" // we only care about the 1st value
            value
        }
    }


    private companion object {

        val KEY_NAME = stringPreferencesKey(
            name = "name"
        )
    }
}
