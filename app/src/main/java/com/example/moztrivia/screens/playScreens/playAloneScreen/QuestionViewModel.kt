package com.example.moztrivia.screens.playScreens.playAloneScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.data.questionsData.DataOrException
import com.example.moztrivia.model.questionsModel.QuestionItem
import com.example.moztrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository):ViewModel(){

    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null,true,Exception("")))

    init {
        getAllQuestion()
    }
    private fun getAllQuestion(){
        viewModelScope.launch {
            data.value.loading=true
            data.value=repository.getAllQuestion()

            if(data.value.data.toString().isNotEmpty()){
                data.value.loading=false
            }
        }
    }

}