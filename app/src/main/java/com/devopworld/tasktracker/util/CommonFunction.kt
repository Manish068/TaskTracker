package com.devopworld.tasktracker.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object CommonFunction {


    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): String? {
        return timeStamp?.let { FORMATTER.format(timeStamp) }
    }

    @TypeConverter
    fun datefromTimeStamp(timeStamp: Long?): String? {
        return timeStamp?.let { DATE_FORMATTER.format(timeStamp) }
    }

    @TypeConverter
    fun dateTimefromTimeStamp(timeStamp: Long?): String? {
        return timeStamp?.let { DATETIME_FORMATTER.format(timeStamp) }
    }

    @TypeConverter
    fun dateToTimestamp(timeStamp: String?): Long? {
        return timeStamp?.let { DATETIME_FORMATTER.parse(it)?.time }
    }


    fun TodayDate():String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))



    fun String.getDateInAnotherFormat(inputFormat: String, outputFormat: String): String =
        SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
            ?.let { SimpleDateFormat(outputFormat, Locale.getDefault()).format(it) } ?: ""


    val FORMATTER = SimpleDateFormat("HH:mm")
    val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd")
    val DATETIME_FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm")

}