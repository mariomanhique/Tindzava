package com.example.moztrivia.data.repositories.repository

import android.util.Log
import com.example.moztrivia.model.questionsModel.QuestionItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.util.UUID
import javax.inject.Inject
import kotlin.properties.Delegates

class QuestionsFsRepository @Inject constructor(private val firestore: FirebaseFirestore){
    private val ref = firestore.collection("questions")
    private var randomIndex by Delegates.notNull<Int>()
    private var document by Delegates.notNull<QuerySnapshot>()

    init {
        getOneQuestion()
    }


     fun getOneQuestion():Flow<QuestionItem?>{
       return ref.whereEqualTo("isAnswered",true)
            .snapshots()
            .map {documents->
                 document = documents
                 randomIndex = (0 until documents.size()).random()
                 documents.documents[randomIndex].toObject<QuestionItem>()
            }
    }

    suspend fun updateQuestion(){
        val questionId = document.documents[randomIndex].id
            ref.document(questionId)
            .update("isAnswered", false)
            .addOnSuccessListener {
                // Return the question text
//                callback(question)
            }
            .addOnFailureListener { exception ->
                // Handle the error
//                callback("Error: ${exception.message}")
            }
    }


}