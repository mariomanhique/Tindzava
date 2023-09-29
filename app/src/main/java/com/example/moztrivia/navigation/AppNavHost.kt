package com.example.moztrivia.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.presentation.screens.authWithCredentials.AuthWithCredentialsViewModel
import com.example.moztrivia.presentation.screens.authWithCredentials.signInWithCredencials.SignInScreen
import com.example.moztrivia.presentation.screens.authWithCredentials.signUpWithCredentials.SignUpWithCredentials
import com.example.moztrivia.presentation.screens.dashboardScreen.DashboardScreen
import com.example.moztrivia.presentation.screens.homeScreen.HomeScreen
import com.example.moztrivia.presentation.screens.onBoardScreens.AgeScreen
import com.example.moztrivia.presentation.screens.onBoardScreens.NicknameScreen
import com.example.moztrivia.presentation.screens.playScreens.playAloneScreen.QuestionViewModel
import com.example.moztrivia.presentation.screens.playScreens.playAloneScreen.TriviaHome
import com.example.moztrivia.presentation.screens.profileScreen.ProfileScreen
import com.example.moztrivia.presentation.screens.splashScreen.SplashScreen
import com.example.moztrivia.presentation.screens.playScreens.ranking.PlayOnline


@Composable
fun AppNavHost(
                viewModel: QuestionViewModel = hiltViewModel(),
               onUpdate:(Player)->Unit,
               playerViewModel: PlayerViewModel,
               authViewModel: AuthWithCredentialsViewModel = hiltViewModel(),
               createPlayer:(Player)->Unit){


    val navController = rememberNavController()

    NavHost(navController = navController, startDestination= NavScreens.SplashScreen.name){

        composable(NavScreens.SplashScreen.name){
            SplashScreen(navController=navController, playerViewModel = playerViewModel)
        }

        composable(NavScreens.PlayAloneScreen.name){
            TriviaHome(viewModel=viewModel, playerViewModel = playerViewModel,onUpdate=onUpdate,navController=navController)
        }

        composable(NavScreens.DashboardScreen.name+"/{score}", arguments = listOf(navArgument(name = "score"){
            type = NavType.StringType
        })){

            var result = it.arguments!!.getString("score")?.split("&")
//            var list = listOf(result)
            DashboardScreen(
                navController = navController,
                score = result!![0].toInt(),
                rightAnswers = result[1].toInt(),
                wrongAnswers = result[2].toInt())
        }

        composable(NavScreens.HomeScreen.name){
            HomeScreen(
                navController=navController,
                playerViewModel = playerViewModel,
                authViewModel = authViewModel
                )
        }

        composable(NavScreens.NicknameScreen.name){
            NicknameScreen(navController=navController, playerViewModel=playerViewModel)
        }

        composable(NavScreens.AgeScreen.name+"/{argName}", arguments = listOf(navArgument(name = "argName"){
            type= NavType.StringType
        })){
            AgeScreen(navController=navController,
                it.arguments?.getString("argName"),
                playerViewModel=playerViewModel,
                createPlayer)
        }

        composable(NavScreens.ProfileScreen.name){

            ProfileScreen(navController = navController)

        }

        composable(NavScreens.LoginScreen.name){

            SignInScreen(
                navController = navController,
                authViewModel=authViewModel
                )

        }

        composable(NavScreens.SignUpScreen.name){

         SignUpWithCredentials(
             navController = navController,
             authViewModel = authViewModel,
             playerViewModel = playerViewModel
             )

        }

        composable(NavScreens.PlayOnline.name){

         PlayOnline(navController = navController)

        }
    }


}