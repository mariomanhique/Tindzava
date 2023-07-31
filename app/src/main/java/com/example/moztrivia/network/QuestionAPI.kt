package com.example.moztrivia.network

import com.example.moztrivia.model.questionsModel.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
//
    @GET("world.json")
    suspend fun getAllQuestion(): Question


//    @GET("world.json")
//    suspend fun getAllQuestions(): Response<Question>

}




