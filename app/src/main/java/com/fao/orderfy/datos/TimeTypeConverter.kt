package com.fao.orderfy.datos
import androidx.room.TypeConverter
import java.sql.Time

class TimeTypeConverter {
    @TypeConverter
    fun toSqlTime(value: Long?): Time? {
        return value?.let { Time(it) }
    }

    @TypeConverter
    fun fromSqlTime(value: Time?): Long? {
        return value?.time
    }
}
