package com.devopworld.tasktracker.util

sealed class MainEvent {
    object NamedCachedSuccess : MainEvent()
    class CachedNameFetchSuccess(val name: String) : MainEvent()
}