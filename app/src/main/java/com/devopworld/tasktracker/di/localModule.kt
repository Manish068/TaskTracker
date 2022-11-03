package com.devopworld.tasktracker.di

import com.devopworld.tasktracker.data.database.DaoProvider
import com.devopworld.tasktracker.data.database.DatabaseProvider
import com.devopworld.tasktracker.repository.TaskRepository
import com.devopworld.tasktracker.repository.UserPreferenceProvider
import com.devopworld.tasktracker.repository.UserPreferencesRepository
import com.devopworld.tasktracker.repository.UserPreferencessRepositoryimpl
import com.devopworld.tasktracker.viewmodel.MainViewModel
import com.devopworld.tasktracker.viewmodel.DataStorePreference
import com.devopworld.tasktracker.viewmodel.DataStorePreferenceImpl
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val localModule= module {

    //repository
    single { TaskRepository(get()) }
    single <UserPreferencesRepository> { UserPreferencessRepositoryimpl(get()) }

    // Providers
    single { DatabaseProvider(get()) }
    single { DaoProvider(get()) }
    single { UserPreferenceProvider(get()) }
    single<DataStorePreference> { DataStorePreferenceImpl(get()) }
    //viewmodel
    viewModel { PreferenceViewModel(get()) }
    viewModel{ MainViewModel(get()) }



}