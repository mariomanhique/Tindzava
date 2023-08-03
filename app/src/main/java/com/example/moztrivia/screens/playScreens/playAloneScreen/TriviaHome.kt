package com.example.moztrivia.screens.playScreens.playAloneScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.widgets.HandleBackButtonPress


@Composable
fun TriviaHome(viewModel: QuestionViewModel,playerViewModel: PlayerViewModel,onUpdate:(Player)->Unit,navController: NavController){

    val context = LocalContext.current
    Questions(viewModel=viewModel, playerViewModel = playerViewModel,onUpdate=onUpdate,navController = navController)

    HandleBackButtonPress {

        Toast.makeText(context,"Pressed",Toast.LENGTH_SHORT).show()
    }

}




@Composable
fun Questions(viewModel: QuestionViewModel,playerViewModel:PlayerViewModel,onUpdate:(Player)->Unit,navController: NavController) {

    var questionIndex = remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current

    val questions = viewModel.data.value.data?.toMutableList()

    if(viewModel.data.value.loading==true){
        Column(modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

        }
        Toast.makeText(context, "Shoo: Shoo", Toast.LENGTH_SHORT).show()
    }else{
        //This retrieves the first questionItem
        val question = try {
            questions?.get(questionIndex.value)
        }catch (ex: Exception){
            null
        }
        if (questions!=null){
            QuestionDisplay(question = question!!,questionIndex=questionIndex,viewModel=viewModel,navController=navController, playerViewModel = playerViewModel,onUpdate=onUpdate ){
                questionIndex.value=it+1
            }

        }

    }
}
