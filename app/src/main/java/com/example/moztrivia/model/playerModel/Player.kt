package com.example.moztrivia.model.playerModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "player_tbl")
data class Player(
    @PrimaryKey
    val uuid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "nickname")
    val nickname:String,
    @ColumnInfo(name = "age")
    val age:Int,
    @ColumnInfo(name="score")
    var score:Int=0,
    @ColumnInfo(name = "lives")
    var lives: Int = 3
)
