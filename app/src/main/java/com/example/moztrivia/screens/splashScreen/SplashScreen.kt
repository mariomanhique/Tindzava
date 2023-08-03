package com.example.moztrivia.screens.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.navigation.NavController
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.NavScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController,playerViewModel: PlayerViewModel){
    val player = playerViewModel.player.collectAsState()

    val scale = remember {
        Animatable(0f)
    }

    //the key the set to true, means that the value will never change and the coroutine will run once.
    LaunchedEffect(key1=true){
        scale.animateTo(
            targetValue = 5f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 400,
           easing = {
               OvershootInterpolator(4f).getInterpolation(it)
           }
        ))
        delay(3000L)
        if (player.value!=null){
            CoroutineScope(Dispatchers.Main).launch{
                navController.navigate(route = NavScreens.HomeScreen.name,
                    builder = {

                        popUpTo(route = NavScreens.SplashScreen.name){
                            inclusive=true
                        }
                    })
            }
        } else{
            CoroutineScope(Dispatchers.Main).launch{
                navController.navigate(route = NavScreens.NicknameScreen.name,
                    builder = {

                        popUpTo(route = NavScreens.SplashScreen.name){
                            inclusive=true
                        }
                    })

            }
        }


    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(imageVector = Icons.Default.Home,
            contentDescription = "Logo",
        modifier = Modifier.scale(scale=scale.value))
    }
}