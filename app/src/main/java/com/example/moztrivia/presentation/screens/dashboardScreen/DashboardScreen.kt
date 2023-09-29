package com.example.moztrivia.presentation.screens.dashboardScreen

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moztrivia.R
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.util.AppColors
import com.example.moztrivia.widgets.HandleBackButtonPress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DashboardScreen(navController: NavController,
                    score:Int=4,
                    wrongAnswers:Int=1,
                    rightAnswers:Int=1){

    val context = LocalContext.current
    Result(navController =navController,
            score = score,
            wrongAnswers = wrongAnswers,
            rightAnswers = rightAnswers)

    HandleBackButtonPress {

        Toast.makeText(context,"Pressed",Toast.LENGTH_SHORT).show()
    }

}
@Composable
fun Result(navController: NavController,
                    score:Int=4,
                    wrongAnswers:Int=1,
                    rightAnswers:Int=1
                    ){

    val context= LocalContext.current
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.sound_one)

    var visible by remember { mutableStateOf(true) }
    var scoreCount by remember { mutableStateOf(score) }

    val density = LocalDensity.current
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
            // Offsets the content by 1/3 of its width to the left, and slide towards right
            // Overwrites the default animation with tween for this slide animation.
            -fullWidth / 3
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
            horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(text = "Resultados", modifier = Modifier, fontWeight = FontWeight.Normal,
//                color = AppColors.mOffWhite,
                letterSpacing = 1.sp,
                fontSize = 25.sp)

            Spacer(modifier = Modifier.height(30.dp))


            Text(text = "GAME OVER", modifier = Modifier.fillMaxHeight(0.1f),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
//                color = AppColors.mOffWhite,
                letterSpacing = 4.sp,
                fontSize = 40.sp
            )

                Text(text = "Pontuação",style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
//                    color = AppColors.mOffWhite,
                    letterSpacing = 4.sp,
                    fontSize = 25.sp, modifier = Modifier.padding(2.dp))

                Card(modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(120.dp)
                    .padding(10.dp),
                colors = CardDefaults
                    .cardColors(containerColor = Color(0xff42B5A4)),
//                shape = CircleShape,
                border = BorderStroke(3.dp, color = AppColors.mYellow)
                ) {

                    Row(modifier = Modifier
                        .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically)
                    {

                        Text(text = "$scoreCount",style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color =MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 4.sp,
                        fontSize = 25.sp)
                }
            }

        Card(elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff42B5A4)))
        {

            Row(modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(150.dp)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
                Column(modifier = Modifier
                    .width(150.dp)
                    .padding(5.dp)) {
                    //Acertos
                    Text(text ="Acertos",
                        modifier = Modifier
                            .padding(4.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp,
                        fontSize = 18.sp)

                    Card(modifier = Modifier
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(containerColor = (MaterialTheme.colorScheme.surface))
                    )
                    {
                        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {

                        Text(text = "$rightAnswers", modifier = Modifier, fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = 1.sp,
                            fontSize = 25.sp)
                        }
                    }
                }

                Column(modifier = Modifier
                    .width(150.dp)
                    .padding(5.dp)) {
                    //Erros
                    Text(text ="Erros",
                        Modifier
                            .padding(4.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                       color= MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp,
                        fontSize = 18.sp)

                    Card(modifier = Modifier
                        .fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = (MaterialTheme.colorScheme.surface))
                    )
                    {
                        Row(modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            Text(text = "$wrongAnswers",
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onBackground,
                                letterSpacing = 1.sp,
                                fontSize = 25.sp)
                        }

                    }
                }
            }
        }

            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {

                CoroutineScope(Dispatchers.Main).launch{
                    mp.start()
                    delay(250L)
                    if(mp.isPlaying) mp.stop()

                    navController.popBackStack(NavScreens.PlayAloneScreen.name,inclusive = false)


                }

            }, modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(60.dp)
                .padding(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
            ) {

                Text(text = "Repetir!",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp,
                    fontSize = 18.sp)
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    mp.start()
                    delay(250L)
                    if(mp.isPlaying) mp.stop()

                    //This allows me fall back to HomeScreen and erasing dash from stack.
                    navController.popBackStack(NavScreens.HomeScreen.name,inclusive = false)
                }

            }, modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(60.dp)
                .padding(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {

                Text(text = "Menu!",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp,
                    fontSize = 18.sp)

            }
        }
    }

}