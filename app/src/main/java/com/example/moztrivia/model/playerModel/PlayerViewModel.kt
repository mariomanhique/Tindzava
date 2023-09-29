package com.example.moztrivia.model.playerModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.data.repositories.playerRepository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val repository: PlayerRepository) : ViewModel() {

    private var _player = MutableStateFlow<Player?>(null)

    val player= _player.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlayer().collect{player->

                if(player!=null){
                    _player.value=player
                }

            }
        }
    }

    fun createPlayer(player: Player)=viewModelScope.launch(Dispatchers.IO) {
        repository.createPlayer(player = player)
    }

    fun updateLives(player: Player) = viewModelScope.launch(Dispatchers.IO) {

        repository.updateLives(player = player)

    }


}