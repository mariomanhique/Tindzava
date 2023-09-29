package com.example.moztrivia.presentation.screens.authWithCredentials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.data.authRepository.AuthRepository
import com.example.moztrivia.data.authRepository.utils.await
import com.example.moztrivia.presentation.screens.SignInResult
import com.example.moztrivia.presentation.screens.SignInState
import com.example.moztrivia.presentation.screens.UserData
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthWithCredentialsViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

     private val _state = MutableStateFlow(SignInState())

    val state = _state.asStateFlow()

    fun getCurrentUser() = authRepository.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = null
        )
    }




    private fun onSignInResult(result: SignInResult){
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState(){
        _state.update {
            SignInState()
        }
    }

    fun signIn(email:String,password:String)= viewModelScope.launch {

         val result = try {

             val user = authRepository.signIn(email,password)

             SignInResult(
                    data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl.toString()
                    )
                },
                    errorMessage = null
            )


            }catch (e: FirebaseAuthException){
              e.printStackTrace()
//              if(e is CancellationException) throw e
              SignInResult(
                    null,
                    errorMessage = e.message
                )

            }

        onSignInResult(result = result)


    }

    fun signUp(name:String,email:String,password: String) = viewModelScope.launch {

        val result = try {

            val user = authRepository.signUp(email=email, password = password,name=name)

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl.toString()
                    )
                },
                errorMessage = null
            )
        }catch (e: FirebaseAuthException){
            e.printStackTrace()
//            if(e is CancellationException) throw e
            SignInResult(
                null,
                errorMessage = e.message
            )
        }
        onSignInResult(result = result)
    }

    private fun signOut(){
        authRepository.logout()
    }

}