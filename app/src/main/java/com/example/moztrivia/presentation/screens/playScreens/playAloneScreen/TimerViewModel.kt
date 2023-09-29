package com.example.moztrivia.presentation.screens.playScreens.playAloneScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class TimerViewModel() : ViewModel() {
    private val currentTime = MutableStateFlow(0L)
    private val isTimeRunning = MutableStateFlow(true)
    companion object {
        private const val TIMER_KEY = "timer_value"
    }

    val timer = combine(currentTime,isTimeRunning){ currentTime,isTime->
        if(isTime){

        }
    }

    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue) //Other wise it will start at 9
        while (currentValue>0){
            delay(1000L)
            currentValue--
            //Now we have to notify our UI
            emit(currentValue)
        }
    }

//    private val _timerState = savedStateHandle.getLiveData(TIMER_KEY, 0L)
//    val timerState: LiveData<Long> = _timerState
//
//    fun setTimerValue(value: Long) {
//        _timerState.value = value
//    }


}