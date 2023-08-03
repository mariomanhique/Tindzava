package com.example.moztrivia.repositories.repository

import android.util.Log
import com.example.moztrivia.data.questionsData.DataOrException
import com.example.moztrivia.model.questionsModel.QuestionItem
import com.example.moztrivia.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {

    private val dataOrException = DataOrException<
        ArrayList<QuestionItem>,
        Boolean,
        Exception>()

    //we call this dataOrException a metaData

    suspend fun getAllQuestion(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading=true
            dataOrException.data=api.getAllQuestion()
            if(dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading=false
            }

        }catch (exception: Exception){
            dataOrException.exception=exception
            Log.d("Data Load", "getAllQuestion:${dataOrException.exception!!.localizedMessage} ")
        }

        return dataOrException
    }

    //The above works, the list is going to give us the data that we want, but there better ways.
    //will use a wrapper fro the data, the wrapper will be a generic that receives anytime and a boolean to be toggled
    //whether it was success or failure



}