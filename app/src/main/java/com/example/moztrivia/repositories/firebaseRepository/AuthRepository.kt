package com.example.moztrivia.repositories.firebaseRepository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser:FirebaseUser?

    suspend fun signIn(email:String,password:String):Resource<FirebaseUser>
    suspend fun signUp(email:String,password:String,name:String):Resource<FirebaseUser>
    fun logout()
}