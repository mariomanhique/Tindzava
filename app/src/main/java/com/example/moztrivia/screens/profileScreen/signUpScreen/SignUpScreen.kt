package com.example.moztrivia.screens.profileScreen.signUpScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moztrivia.component.InputFields
import com.example.moztrivia.model.authViewModel.AuthViewModel
import com.example.moztrivia.repositories.firebaseRepository.Resource


@Composable
fun SignUpScreen(navController: NavController, viewModel: AuthViewModel){

    val signUpFlow = viewModel.signUpFlow.collectAsState()
    Column(modifier=Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        var name = remember {
            mutableStateOf("")
        }

        var email = remember {
            mutableStateOf("")
        }

        var password = remember {
            mutableStateOf("")
        }


        Spacer(modifier = Modifier.padding(30.dp))

        InputFields(value=name.value,textTransformation = VisualTransformation.None,placeHolder = "Name"){
            name.value = it
        }

        InputFields(value=email.value, textTransformation =VisualTransformation.None,placeHolder = "Email"){
            email.value = it
        }

        InputFields(value=password.value, textTransformation = PasswordVisualTransformation(),placeHolder = "Password"){
            password.value = it
        }

        Button(onClick = {
            viewModel?.signUp(name.value,email.value,password.value)
        }) {
            Text(text = "Sign-Up")
        }

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = Color.White)){
                append("Already have an account? ")
            }

            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.White)){
                append("Sign-In")
            }
        },modifier=Modifier.clickable( onClick = {

        }))

        signUpFlow.value.let {
            when(it){
                is Resource.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context,"${it.exception.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        strokeWidth = 10.dp
                    )
                }
                is Resource.Success -> {

//                    var user = User(Base64Custom.encodeToBase64
//                        (email.value),
//                        name.value,
//                        email.value,
//                        password.value)

//                    userViewModel.saveUser(user =user)

                    LaunchedEffect(Unit){

                    }

                }
                null -> {

                }
            }
        }
    }
}
