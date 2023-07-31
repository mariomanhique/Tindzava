package com.example.moztrivia.model.questionsModel

data class QuestionItem(
    val answer: String,
    val category: String,
    val choices: List<String>,
    val question: String
)