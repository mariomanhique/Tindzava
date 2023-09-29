package com.example.moztrivia.presentation.screens.playScreens.playAloneScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.util.AppColors
import com.example.moztrivia.util.IndexTracker.generateRandomIndex
import com.example.moztrivia.widgets.HandleBackButtonPress
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import  kotlin.random.Random

@Composable
fun TriviaHome(viewModel: QuestionViewModel,
               playerViewModel: PlayerViewModel,
               onUpdate:(Player)->Unit,
               navController: NavController){

    var isDialogShown by remember {
        mutableStateOf(false)
    }


    val context = LocalContext.current

    Questions(viewModel=viewModel, playerViewModel = playerViewModel,onUpdate=onUpdate,navController = navController)

    HandleBackButtonPress {
        isDialogShown=true
    }

    if(isDialogShown){
        CustomerDialog(onDismiss = {
            isDialogShown=false
        }, onConfirm = {
            //This allows me fall back to HomeScreen and erasing dash from stack.
            navController.popBackStack(NavScreens.HomeScreen.name,inclusive = false)
        })
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerDialog(
    onDismiss:()->Unit,
    onConfirm:()->Unit,
){
    Dialog(onDismissRequest = {
        onDismiss.invoke()
    },
    properties = DialogProperties(
        usePlatformDefaultWidth = false
    )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = CircleShape.copy(all = CornerSize(15.dp)),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .border(1.dp, color = Color.White, shape = RoundedCornerShape(15.dp)),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)

        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp))
            {
                Text(text = "Confirmação",
                    Modifier.align(alignment = Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp,
                    fontSize = 25.sp)

                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Tem certeza que desejar sair do jogo?",
                        Modifier
                            .padding(bottom = 10.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Button(onClick = (
                        onDismiss
                    ),
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(text = "Continuar",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp)
                    }

                    Button(onClick = {
                        onConfirm()
                    },
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
                    shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(text = "Sair",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp)
                    }


                }
            }
        }
    }
}




@Composable
fun Questions(viewModel: QuestionViewModel, playerViewModel:PlayerViewModel, onUpdate:(Player)->Unit, navController: NavController) {



    val questions = viewModel.question.collectAsStateWithLifecycle().value
    val state = viewModel.state.value

    var questionIndex = remember {
        mutableStateOf(0)
    }

    var selectedIndices = mutableSetOf<Int>()
    val context = LocalContext.current
    val tempHolder = remember {
        mutableStateOf(0)
    }


    if(state){
        Column(modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

        }
    }else{
        //This retrieves the first questionItem
//        val question = try {
//            questions[questionIndex.value]
//        }catch (ex: Exception){
//            null
//        }
        QuestionDisplay(
            question = questions!!,
            questionIndex= questionIndex,
            viewModel= viewModel,
            navController= navController,
            playerViewModel = playerViewModel,
            onUpdate= onUpdate

        )

    }
}
