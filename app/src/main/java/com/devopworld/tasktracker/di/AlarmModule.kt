package com.devopworld.tasktracker.module

import com.devopworld.tasktracker.Alarm.ScheduleAlarm
import com.devopworld.tasktracker.Alarm.ScheduleAlarmImpl
import com.devopworld.tasktracker.Alarm.ShowAlarm
import com.devopworld.tasktracker.Notification.*
import com.devopworld.tasktracker.permission.*
import com.devopworld.tasktracker.viewmodel.AlarmViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AlarmModule = module {
    //provider
    factory<AndroidVersion> { AndroidVersionImpl() }
    factory<PermissionChecker> { PermissionCheckerImpl(androidContext()) }
    factory<AlarmPermission> { AlarmPermissionImpl(get(), get()) }
    factory<NotificationInteractor> { NotificationInteractorImpl(get()) }
    factory<ScheduleAlarm> { ScheduleAlarmImpl(get()) }
    factory { TaskNotificationScheduler(androidContext()) }
    factory { ShowAlarm(get(), get()) }
    factory { TaskNotificationChannel(androidContext()) }
    factory { TaskNotification(androidContext(), get(),get(),get()) }


    //viewmodel
    viewModel { AlarmViewModel(get()) }

}