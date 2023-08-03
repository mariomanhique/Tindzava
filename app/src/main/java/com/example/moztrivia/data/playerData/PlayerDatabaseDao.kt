package com.example.moztrivia.data.playerData

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

    @Query("select * from player_tbl where uuid=:uuid")
    suspend fun getPlayerById(uuid: String):Player

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPlayer(player:Player)

    @Update
    suspend fun updateLives(player: Player)


}