package com.example.moztrivia.presentation.screens.playScreens.playAloneScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.model.questionsModel.DataOrException
import com.example.moztrivia.model.questionsModel.QuestionItem
import com.example.moztrivia.data.repositories.repository.QuestionRepository
import com.example.moztrivia.data.repositories.repository.QuestionsFsRepository
import com.example.moztrivia.model.questionsModel.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: QuestionRepository,
    private val fsRepository: QuestionsFsRepository
):ViewModel(){

    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null,true,Exception("")))

    private val _question = MutableStateFlow<QuestionItem?>(QuestionItem())
    val question = _question.asStateFlow()
    val state  = mutableStateOf(false)
    init {
        getAllQuestion()
    }
    private fun getAllQuestion() = viewModelScope.launch {
        state.value = true
        fsRepository.getOneQuestion().collect{
            if(it != null){
                _question.value = it
            }
            state.value = false
        }

    }

    fun updateQuestion(){
        viewModelScope.launch {
            fsRepository.updateQuestion()
        }
    }

//    private fun getAllQuestion() = viewModelScope.launch {
//        data.value.loading=true
//        data.value=repository.getAllQuestion()
//
//        if(data.value.data.toString().isNotEmpty()){
//            data.value.loading=false
//        }
//    }

//    fun getQuestion(){
//        viewModelScope.launch {
//            fsRepository.getQuestion().collect{
//                _question.value =
//            }
//        }
//    }
    suspend fun getAllQ(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        return repository.getAllQuestion()
    }


}