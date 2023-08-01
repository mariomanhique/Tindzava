package com.example.moztrivia.model.authViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moztrivia.repositories.firebaseRepository.AuthRepository
import com.example.moztrivia.repositories.firebaseRepository.AuthRepositoryImpl
import com.example.moztrivia.repositories.firebaseRepository.Resource
import com.example.moztrivia.screens.onBoardScreens.audioPlayer
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val authRepository: AuthRepository):ViewModel() {

    private val _loginFlow= MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow:StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signUpFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signUpFlow:StateFlow<Resource<FirebaseUser>?> = _signUpFlow

    val currentUser: FirebaseUser
        get() = authRepository.currentUser!!

    init {
        if(authRepository.currentUser!=null){
            _loginFlow.value=Resource.Success(authRepository.currentUser!!)
        }
    }

    fun signIn(email:String,password:String)= viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result= authRepository.signIn(email = email, password = password)
        _loginFlow.value = result
    }

    fun signUp(name:String,email:String,password: String) = viewModelScope.launch {
        _signUpFlow.value = Resource.Loading
        val result = authRepository.signUp(email=email, password = password,name=name)
        _signUpFlow.value = result
    }

    fun signOut(){

        authRepository.logout()
        _loginFlow.value=null
        _signUpFlow.value=null

    }

}