package com.example.moztrivia.presentation.screens.authWithCredentials.signUpWithCredentials


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.moztrivia.R
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.presentation.screens.authWithCredentials.AuthWithCredentialsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpWithCredentials(
    navController: NavController,
    authViewModel: AuthWithCredentialsViewModel,
    playerViewModel: PlayerViewModel
    ){
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val state = authViewModel.state.collectAsStateWithLifecycle().value
    val player = playerViewModel.player.collectAsStateWithLifecycle().value
//    val image = imageResource(id = R.drawable.register_page)

    val nameValue = remember { mutableStateOf(player?.nickname) }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
//            Image(image)
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(30.dp))
                .padding(10.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign Up", fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.padding(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    nameValue.value?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                nameValue.value = it },
                            label = { Text(text = "Name") },
                            placeholder = { Text(text = "Name") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }

                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = { Text(text = "Email Address") },
                        placeholder = { Text(text = "Email Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )


                    OutlinedTextField(
                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        label = { Text(text = "Password") },
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.password_eye),
                                    contentDescription = "",
                                    tint = if (passwordVisibility.value) Color.White else Color.Gray
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )

//                    OutlinedTextField(
//                        value = confirmPasswordValue.value,
//                        onValueChange = { confirmPasswordValue.value = it },
//                        label = { Text(text = "Confirm Password") },
//                        placeholder = { Text(text = "Confirm Password") },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                confirmPasswordVisibility.value = !confirmPasswordVisibility.value
//                            }) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.password_eye),
//                                    contentDescription = "",
//                                    tint = if (confirmPasswordVisibility.value) Color.White else Color.Gray
//                                )
//                            }
//                        },
//                        visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
//                        else PasswordVisualTransformation()
//                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if(state.isSignInSuccessful) {
                            navController.navigate(NavScreens.PlayOnline.name)
                        }
//                        authViewModel.resetState()

                    }

                    Button(onClick = {

                        if(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty()){
                            nameValue.value?.let {
                                authViewModel.signUp(
                                    name= it,
                                    email = emailValue.value,
                                    password = passwordValue.value
                                )
                            }

                        }

                    }, modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)) {
                        Text(text = "Sign Up", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Login Instead",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(NavScreens.LoginScreen.name){

                                launchSingleTop = true

                                restoreState=true

                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))

                }

            }
        }
    }
}