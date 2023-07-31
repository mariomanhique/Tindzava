package com.example.moztrivia.util

import androidx.room.TypeConverter
import java.util.UUID

object UUIDConverter {

    @TypeConverter
    fun fromUIID(uuid: UUID):String?{
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string:String):UUID?{
        return UUID.fromString(string)
    }

}