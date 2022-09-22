package com.devopworld.tasktracker.util

import androidx.room.TypeConverter
import java.sql.Date
import java.text.SimpleDateFormat

class Converters {

    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): String? {
        return timeStamp?.let { FORMATTER.format(timeStamp) }
    }

//    @TypeConverter
//    fun dateToTimestamp(timeStamp: String?): Long? {
//        return timeStamp?.let { FORMATTER.parse(it)?.time }
//    }

    @TypeConverter
    fun DatefromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }

    companion object{

        val FORMATTER = SimpleDateFormat("E,DD MMMM HH:mm")
    }
}