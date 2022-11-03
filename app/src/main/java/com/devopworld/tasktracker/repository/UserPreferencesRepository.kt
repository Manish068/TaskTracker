package com.devopworld.tasktracker.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun setName(name: String)

    suspend fun getName(): Result<String>

    suspend fun setKeyAndValue(key:String,value:String)

    suspend fun getValueFromKey(key: String):Result<String>

}