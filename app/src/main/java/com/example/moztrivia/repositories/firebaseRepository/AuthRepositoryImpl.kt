package com.example.moztrivia.repositories.firebaseRepository

import com.example.moztrivia.repositories.firebaseRepository.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth): AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun signIn(email: String, password: String): Resource<FirebaseUser> {
      return try{
          val result= auth.signInWithEmailAndPassword(email,password).await()
          Resource.Success(result = result.user!!)

      }catch (e:Exception){
          e.printStackTrace()
          Resource.Failure(e)
      }

    }

    override suspend fun signUp(email: String, password: String,name: String): Resource<FirebaseUser> {
        return try {

            val result = auth.createUserWithEmailAndPassword(email,password).await()
            result.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            Resource.Success(result = result.user!!)

        }catch (e:Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override fun logout() {
        auth.signOut()
    }
}