package com.example.moztrivia.data.player

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.moztrivia.model.playerModel.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDatabaseDao {

    @Query("select * from player_tbl")
    fun getPlayer(): Flow<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createPlayer(player:Player)

    @Update
    fun updateLives(player: Player)


}