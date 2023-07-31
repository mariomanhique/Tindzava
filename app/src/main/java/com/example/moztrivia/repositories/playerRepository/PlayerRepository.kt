package com.example.moztrivia.repositories.playerRepository

import com.example.moztrivia.data.playerData.PlayerDatabaseDao
import com.example.moztrivia.model.playerModel.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlayerRepository @Inject constructor(private var playerDao: PlayerDatabaseDao) {

    suspend fun createPlayer(player:Player){
        playerDao.createPlayer(player = player)
    }

    suspend fun getPlayerById(uuid: String){
        playerDao.getPlayerById(uuid = uuid)
    }

     fun getPlayer():Flow<Player>{
        return playerDao.getPlayer().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun updateLives(player: Player){
        playerDao.updateLives(player = player)
    }





}