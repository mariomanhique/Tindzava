package com.example.moztrivia.model.playerModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.data.playerData.PlayerDatabaseDao
import com.example.moztrivia.repositories.playerRepository.PlayerRepository
import com.example.moztrivia.util.UUIDConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val repository: PlayerRepository) : ViewModel() {

    private var _player = MutableStateFlow<Player?>(null)

    val player= _player.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlayer().distinctUntilChanged().collect(){playerId->
                if (playerId!=null){
                    _player.value=playerId
                }
            }
        }
    }

    fun createPlayer(player: Player)=viewModelScope.launch {
        repository.createPlayer(player = player)
    }

    fun updateLives(player: Player) = viewModelScope.launch {
        repository.updateLives(player = player)
    }


}