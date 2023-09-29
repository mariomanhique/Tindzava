package com.example.moztrivia.data.repositories.playerRepository

import com.example.moztrivia.data.player.PlayerDatabaseDao
import com.example.moztrivia.model.playerModel.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepository @Inject constructor(private var playerDao: PlayerDatabaseDao) {

    suspend fun createPlayer(player:Player){
        playerDao.createPlayer(player = player)
    }



     suspend fun getPlayer():Flow<Player>{
        return playerDao.getPlayer()
    }

    suspend fun updateLives(player: Player){
        playerDao.updateLives(player = player)
    }




}



