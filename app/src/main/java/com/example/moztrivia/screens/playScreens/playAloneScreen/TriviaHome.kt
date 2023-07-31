package com.example.moztrivia.screens.playScreens.playAloneScreen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.model.playerModel.PlayerViewModel
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TriviaHome(viewModel: QuestionViewModel,playerViewModel: PlayerViewModel,onUpdate:(Player)->Unit,navController: NavController){

    Questions(viewModel=viewModel, playerViewModel = playerViewModel,onUpdate=onUpdate,navController = navController)

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
