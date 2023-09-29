package com.example.moztrivia.util

import androidx.room.Dao
import androidx.room.TypeConverter
import java.util.UUID

object UUIDConverter {


    @TypeConverter
    @JvmStatic
    fun fromUIID(uuid: UUID):String?{
        return uuid.toString()
    }

    @TypeConverter
    @JvmStatic
    fun uuidFromString(string:String):UUID?{
        return UUID.fromString(string)
    }

}