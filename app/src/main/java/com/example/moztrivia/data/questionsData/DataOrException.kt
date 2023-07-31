package com.example.moztrivia.data.questionsData

data class DataOrException<T,Boolean,E:Exception>(
    var data:T?=null,
    var loading: Boolean?=null,
    var exception: E?=null
)
