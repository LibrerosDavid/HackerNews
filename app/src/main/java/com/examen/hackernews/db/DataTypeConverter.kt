package com.examen.hackernews.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime

object DataTypeConverter {
    @TypeConverter
    fun stringToLocalDataTime(time: String?): LocalDateTime? {

        if (time == null || time.compareTo("null") == 0) {
            return null
        } else {

            return LocalDateTime.parse(time)
            //return LocalDateTime.parse(time)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDataTimeToString(time: LocalDateTime?): String? {
        return time.toString() ?: null
    }
}