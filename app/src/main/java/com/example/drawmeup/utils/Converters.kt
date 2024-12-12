package com.example.drawmeup.utils

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        return ArrayList(value.split(",").map { it.trim() })
    }

    @TypeConverter
    fun toString(list: ArrayList<String>): String {
        return list.joinToString(",")
    }
}