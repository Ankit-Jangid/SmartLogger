package com.logger.utils


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(value: Map<String, Any>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<String, Any> {
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return gson.fromJson(value, type)
    }
}
