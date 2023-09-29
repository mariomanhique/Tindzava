package com.example.moztrivia.presentation.screens.homeScreen

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.moztrivia.R
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.presentation.screens.authWithCredentials.AuthWithCredentialsViewModel
import com.example.moztrivia.util.AppColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController,
               playerViewModel: PlayerViewModel,
               authViewModel: AuthWithCredentialsViewModel
               ){

    val context= LocalContext.current
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.sound_one)
    val vs: MediaPlayer = MediaPlayer.create(context, R.raw.sound_one)
    val prfl: MediaPlayer = MediaPlayer.create(context, R.raw.sound_one)


    var player = playerViewModel.player.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                Card(modifier = Modifier
                    .fillMaxWidth(0.60f)
                    .height(80.dp)
                    .padding(start = 10.dp),
                    colors = CardDefaults.cardColors(AppColors.mYellow),
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(text = buildAnnotatedString {
                        withStyle(style= SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.background,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp
                        )){
                            append("${player?.nickname}")
                        }

                    }, modifier = Modifier.padding(start=7.dp, top = 7.dp, bottom = 5.dp))

                    Text(text = buildAnnotatedString {
                        withStyle(style= SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.background,
                            letterSpacing = 3.sp,
                            fontSize = 20.sp
                        )){
                            append("${player?.score}")
                        }
                        withStyle(style= SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.background,
                            letterSpacing = 2.sp,
                            fontSize = 20.sp
                        )){
                            append("pts")
                        }

                    }, modifier = Modifier.padding(start=7.dp, top = 5.dp, bottom = 7.dp))
                }

                Card(modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(80.dp)
                    .padding(0.dp),
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id =R.drawable.heart),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp))
                        Text(text = "${player?.lives}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = 1.sp,
                            fontSize = 16.sp
                            )
                    }

                }

            }



            Text(text = "TINDZAVA", modifier = Modifier.fillMaxHeight(0.1f),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 4.sp,
                fontSize = 40.sp
            )
            Text(text = "Quiz Baseado em polémicas",
                modifier = Modifier.fillMaxHeight(0.1f),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.padding(30.dp))

//            Text(text = "Jogar", modifier = Modifier,
//                style = MaterialTheme.typography.headlineLarge,
//                fontWeight = FontWeight.ExtraBold,
//                color = MaterialTheme.colorScheme.onBackground,
//                letterSpacing = 4.sp,
//                fontSize = 30.sp
//            )

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch{
                        mp.start()
                        delay(300L)


                    if(player?.lives!=0){
                        navController.navigate(route = NavScreens.PlayAloneScreen.name){

//                            popUpTo(NavScreens.HomeScreen.name){
//                                inclusive=true
//                            }

                            launchSingleTop=true

                            restoreState=true
                        }
                    }
                }

            }, modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(65.dp)
                .padding(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(6.dp)
            ) {

                Text(text = "Sozinho",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp,
                    fontSize = 20.sp)
            }



            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch{
                    mp.start()
                    delay(250L)
                    if(authViewModel.getCurrentUser()!=null){
                        navController.navigate(NavScreens.PlayOnline.name)
                    } else{
                        navController.navigate(NavScreens.LoginScreen.name)
                    }

                }
                //Here I will put a validation, if user has account, goes directly to the screen
                // where he will choose an opponent and if not to account creation.
            }, modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(65.dp)
                .padding(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(6.dp)
            ) {

                Text(text = "Online",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp,
                    fontSize = 20.sp)
            }



            Spacer(modifier = Modifier.padding(10.dp))

            Row() {
                //Here I will put a row of buttons such as settings, profile and so on.


            }
        }
    }

}