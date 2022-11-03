package com.devopworld.tasktracker.viewmodel

import com.devopworld.tasktracker.util.MainEvent
import kotlinx.coroutines.flow.Flow

private const val TAG = "PreferenceViewModel"

abstract class DataStorePreference() {

    abstract fun cacheName(name: String): Flow<MainEvent>
    abstract fun getCacheName(): Flow<MainEvent>
    abstract fun storeValueForKey(key: String, value: String): Flow<MainEvent>
    abstract fun getValueFromKey(key: String): Flow<MainEvent.CachedNameFetchSuccess>

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

//    fun cacheName(
//        name: String
//    ) = flow {
//        userPreferencesRepository.setName(name)
//        emit(MainEvent.NamedCachedSuccess)
//    }
//
//    fun getCachedName() = flow {
//        val result = userPreferencesRepository.getName()
//        val name = result.getOrNull().orEmpty() // don't care if it failed right now but you might
//        emit(MainEvent.CachedNameFetchSuccess(name))
//    }
//
//    fun storeValueForKey(key:String,value: String)=
//        flow {
//            userPreferencesRepository.setKeyAndValue(key,value)
//            emit(MainEvent.NamedCachedSuccess)
//        }
//
//    fun getValueFromKey(key: String) = flow {
//        val result = userPreferencesRepository.getValueFromKey(key)
//        val value = result.getOrNull().orEmpty()
//        emit(MainEvent.CachedNameFetchSuccess(value))
//    }


}