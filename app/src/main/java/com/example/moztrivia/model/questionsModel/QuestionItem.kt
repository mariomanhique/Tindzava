package com.example.moztrivia.model.questionsModel

import java.util.UUID

data class QuestionItem(
    val id: String = UUID.randomUUID().toString(),
    val isAnswered: Boolean = false,
    val answer: String="",
    val category: String="",
    val choices: List<String> = emptyList(),
    val question: String=""
)