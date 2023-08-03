package com.example.moztrivia.screens.onBoardScreens

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moztrivia.R
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.util.AppColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private fun stopSound(mp:MediaPlayer) {
    if (mp.isPlaying) {
        mp.stop()
    }
//    mp.release()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NicknameScreen(navController: NavController,
                   playerViewModel: PlayerViewModel){

    val context= LocalContext.current
    val mp: MediaPlayer= MediaPlayer.create(context,R.raw.sound_one)

    var nickname by remember {
        mutableStateOf("")
    }

    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(durationMillis =200, delayMillis = 200)) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            -fullWidth/2
        } + fadeIn(
            // Overwrites the default animation with tween
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
            // Overwrites the ending position of the slide-out to 200 (pixels) to the right
            200
        } + fadeOut()
    ) {
        // Content that needs to appear/disappear goes here:

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "TINDZAVA",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.mOffWhite,
                letterSpacing = 4.sp,
                fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 40.dp)
            )


            Text(text = "Escolha um apelido para continuar.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                fontSize = 25.sp,
                modifier = Modifier.padding(10.dp)
            )

            Card(modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.5f),
                elevation = CardDefaults.cardElevation(4.dp)) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        value = nickname, onValueChange = {
                            nickname = it
                        }, modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 0.dp),
                        placeholder = {
                            Text(text = "Nickname",
                                fontWeight = FontWeight.Bold,
                                color = AppColors.mGreen,
                                letterSpacing = 1.sp,
                                fontSize = 20.sp)
                        }, textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = AppColors.mGreen,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp)
                        ,
                        singleLine = true
                    )


                    Button(
                        onClick = {

                            if(!nickname.trim().isNullOrEmpty()){
                                CoroutineScope(Dispatchers.Main).launch {
                                    mp.start()
                                    delay(300L)
                                    if(mp.isPlaying) mp.stop()


                                    navController.navigate(
                                        route = NavScreens.AgeScreen.name+"/$nickname",
                                        builder = {
                                            popUpTo(route = NavScreens.NicknameScreen.name){
                                                inclusive=true
                                            }
                                        }
                                    )
                                }
                            }else{
                                CoroutineScope(Dispatchers.Main).launch {
                                    mp.start()
                                    delay(300L)
                                }

                            }


                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(70.dp)
                            .padding(top = 20.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(AppColors.mGreen)
                    ) {
                        Text(text = "Continuar",
                            fontWeight = FontWeight.Bold,
                            color = AppColors.mOffWhite,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp)
                    }

                }
            }
        }

    }


}

@Composable
fun audioPlayer(){
//    val mp: MediaPlayer= MediaPlayer.create(context,R.raw.sound_one)
}