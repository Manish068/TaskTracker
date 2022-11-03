package com.devopworld.tasktracker.module

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val AppModule = module {
    factory { CoroutineScope(SupervisorJob()) }
}