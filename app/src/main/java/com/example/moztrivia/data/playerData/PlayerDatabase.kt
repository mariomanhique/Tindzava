package com.example.moztrivia.data.playerData

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.util.UUIDConverter

@Database(entities = [Player::class], version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class PlayerDatabase: RoomDatabase() {

    abstract fun PlayerDao(): PlayerDatabaseDao
}