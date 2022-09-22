package com.devopworld.tasktracker.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devopworld.tasktracker.repository.UserPreferencesRepository
import com.devopworld.tasktracker.util.MainEvent
import com.devopworld.tasktracker.util.PreferenceConstant.USER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PreferenceViewModel"
@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

//    private val _nameSaved:MutableState<Boolean> = mutableStateOf(false)
//    val nameSaved:State<Boolean> =_nameSaved

//    private val _userName:MutableState<String> = mutableStateOf("")
//    val userName:State<String> =_userName
//    init {
//         viewModelScope.launch {
//            getValueFromKey(USER_NAME).collect {
//                when (it) {
//                    is MainEvent.CachedNameFetchSuccess -> {
//                        _nameSaved.value = it.name.isNotBlank() && it.name.isNotEmpty()
//                        if(nameSaved.value){
//                            _userName.value = it.name
//                        }
//                        this.cancel()
//                        Log.d(TAG, "Named is Saved: ${it.name}")
//                    }
//                }
//                this.cancel()
//            }
//        }
//    }

    fun cacheName(
        name: String
    ) = flow {
        userPreferencesRepository.setName(name)
        emit(MainEvent.NamedCachedSuccess)
    }

    fun getCachedName() = flow {
        val result = userPreferencesRepository.getName()
        val name = result.getOrNull().orEmpty() // don't care if it failed right now but you might
        emit(MainEvent.CachedNameFetchSuccess(name))
    }

    fun storeValueForKey(key:String,value: String)=
        flow {
            userPreferencesRepository.setKeyAndValue(key,value)
            emit(MainEvent.NamedCachedSuccess)
        }

    fun getValueFromKey(key: String) = flow {
        val result = userPreferencesRepository.getValueFromKey(key)
        val value = result.getOrNull().orEmpty()
        emit(MainEvent.CachedNameFetchSuccess(value))
    }




}