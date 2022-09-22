package com.devopworld.tasktracker.di

import android.content.Context
import androidx.room.Room
import com.devopworld.tasktracker.data.database.TaskDatabase
import com.devopworld.tasktracker.util.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,TaskDatabase::class
                .java,DATABASE_NAME
        ).build()

    // provide us database Access Objects(ex : insert , update , delete)
    @Singleton
    @Provides
    fun provideDao(taskDatabase: TaskDatabase) = taskDatabase.taskDao()

}