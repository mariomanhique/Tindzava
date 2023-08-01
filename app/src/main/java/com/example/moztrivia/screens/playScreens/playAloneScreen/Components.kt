package com.example.moztrivia.screens.playScreens.playAloneScreen

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moztrivia.R
import com.example.moztrivia.model.playerModel.Player
import com.example.moztrivia.model.playerModel.PlayerViewModel
import com.example.moztrivia.model.questionsModel.QuestionItem
import com.example.moztrivia.navigation.NavScreens
import com.example.moztrivia.util.AppColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


//@Preview
@Composable
fun QuestionDisplay(question: QuestionItem,
                    questionIndex:MutableState<Int>,
                    viewModel: QuestionViewModel,
                    playerViewModel: PlayerViewModel,
                    onUpdate:(Player)->Unit,
                    navController:NavController,
                    onNextQuestion: (Int)->Unit={}){

    var player = playerViewModel.player.collectAsState()





    var isAnswered by remember {
        mutableStateOf(true)
    }

    val choicesState= remember(question) {
        question.choices.toMutableStateList()
    }

    val answerState= remember(question) {

        mutableStateOf<Int?>(null)

    }

    val correctAnswerState = remember (question){
        mutableStateOf<Boolean?>(null)
    }

    var timer = remember {
        mutableStateOf(10*1000L)
    }

    var isRunning = remember {
        mutableStateOf(true)
    }

    var score by remember {
        mutableStateOf(2)
    }

    var wrongAnswers by remember {
        mutableStateOf(0)
    }

    var rightAnswers by remember {
        mutableStateOf(0)
    }

    var livesCount by remember {
        mutableStateOf(player.value!!.lives)
    }


    val updateAnswer: (Int) -> Unit= remember(question) {
        //This lambda allow us to have an integer to control our index to check the correct choice

        {

            answerState.value=it

            //Return true if choicesState[it] at position "it" is equal to question.answer
            // which means, if question is correct,
            correctAnswerState.value=(choicesState[it]==question.answer)

//            timer+=10L


        }

    }

    val context= LocalContext.current

    val mp: MediaPlayer= MediaPlayer.create(context,R.raw.derrotadoshort)

    val scope = rememberCoroutineScope()
    val scopeAfterTimer = rememberCoroutineScope()

    val pathEffect=PathEffect.dashPathEffect(floatArrayOf(10f,10f), phase = 0f)

    val wrongSound:MediaPlayer = MediaPlayer.create(context, R.raw.sound)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = AppColors.mGreen
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {





            if(questionIndex.value>=1) ShowProgress(score = questionIndex.value)

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Row() {
                    Text(text = "${player.value?.nickname}",
                        fontWeight = FontWeight.Bold,
                        color = AppColors.mOffWhite,
                        letterSpacing = 1.sp,
                        fontSize = 20.sp
                        )
                }
                    Timer(currentTime = timer,
                        isTimeRunning = isRunning,
                        timeRunning = {
//                            isRunning.value=it
                        },
                        handleColor = Color.Green,
                        inactiveBarColor = Color.DarkGray,
                        activeBarColor = Color(0xff37B900),
                        modifier = Modifier.size(45.dp)){

                               mp.start()
                               CoroutineScope(Dispatchers.Main).launch {
                                   delay(1300L)
                                   navController.navigate(
                                       route=NavScreens.DashboardScreen.name+"/$score&$rightAnswers&$wrongAnswers",
                                       builder = {
                                           popUpTo(NavScreens.PlayAloneScreen.name){
                                               inclusive=true
                                           }
                                       }
                                   )

                               }
                    }
                Column(modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id =R.drawable.heart),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp))
                    Text(text = "$livesCount",
                        fontWeight = FontWeight.Bold,
                        color = AppColors.mRed,
                        letterSpacing = 1.sp,
                        fontSize = 16.sp
                    )
                }
            }


            QuestionTracker(counter = questionIndex.value,
                            viewModel.data.value.data!!.size)

//            DrawDottedLine(pathEffect = pathEffect)

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(2.dp),
                    colors = CardDefaults.cardColors(AppColors.mYellow),
                elevation = CardDefaults.cardElevation(5.dp)) {
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center) {
                        Text(text = question.question,
                            modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .align(alignment = Alignment.CenterVertically),
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Justify,
                            lineHeight = 25.sp,
                            color = AppColors.mOffWhite)
                    }

            }

                choicesState.forEachIndexed{index, answerText ->

                    Row(modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .height(53.dp)
                        .border(
                            width = 2.dp, color = Color.White,
                            shape = CircleShape.copy(all = CornerSize(15.dp))
                        )
                        .clip(
                            RoundedCornerShape(
                                topStart = 50.dp,
                                topEnd = 50.dp,
                                bottomEnd = 50.dp,
                                bottomStart = 50.dp
                            )
                        )
                        .background(Color.Transparent),
//                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {

                        var checkCorrectAnswer=correctAnswerState.value == true && index == answerState.value
                        //Fix Deselection of RadioButtons after one is selected TODO
//                        SideEffect {
//                        }

                        RadioButton(selected =answerState.value==index,
                            enabled = isAnswered,

                            onClick = {
                                updateAnswer(index)

                                var playerUpdate = Player(player.value!!.uuid,
                                    player.value!!.nickname,
                                    player.value!!.age,
                                    score = if(score > player.value!!.score)score else  player.value!!.score,
                                    livesCount)

                                if(correctAnswerState.value == false){
                                    livesCount-=1

                                    wrongAnswers = wrongAnswers.plus(1)
//                                    playerViewModel.updateLives(playerUpdate)
                                    wrongSound.start()

                                    if(playerUpdate.lives==0){
                                        CoroutineScope(Dispatchers.Main).launch {

                                            delay(1300L)
                                            navController.navigate(
                                                route=NavScreens.DashboardScreen.name+"/$score&$rightAnswers&$wrongAnswers",
                                                builder = {
                                                    popUpTo(NavScreens.PlayAloneScreen.name){
                                                        inclusive=true
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }else{
                                    score = score.times(2)
                                    rightAnswers = rightAnswers.plus(1)


                                }

                                CoroutineScope(Dispatchers.Main).launch {
                                    if(checkCorrectAnswer){
                                        updateAnswer(index)
                                        delay(500L)
                                        onNextQuestion(questionIndex.value)
//                                        isRunning.value=false
                                        Toast.makeText(context,"${isRunning.value}",Toast.LENGTH_LONG).show()
                                        timer.value =10L*1000L

                                    } else {
                                        updateAnswer(index)
                                        isAnswered=false
                                        delay(500L)
                                        onNextQuestion(questionIndex.value)
                                        //After question is updated we enable the RadioButtons
                                        isAnswered=true
//                                        isRunning.value=false
                                        Toast.makeText(context,"${isRunning.value}",Toast.LENGTH_LONG).show()
                                        timer.value =10L*1000L
                                    }

                                }

                                onUpdate(playerUpdate)

                            }, modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor=
                                if(checkCorrectAnswer){
                                    Color.Green
                                } else{
                                    AppColors.mRed
                                }, unselectedColor = AppColors.mOffWhite,
                                disabledSelectedColor =
                                if(checkCorrectAnswer){
                                    Color.Green
                                } else{
                                    AppColors.mRed
                                }, disabledUnselectedColor = AppColors.mOffWhite
                            ))//end radiox

                        val annotatedString= buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color =if(checkCorrectAnswer){
                                    Color.Green.copy()
                                } else if(correctAnswerState.value==false && index == answerState.value) {

                                    AppColors.mRed

                                }else{
                                    AppColors.mOffWhite

                                })){
                                append(text = answerText)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            Card(modifier = Modifier
                                .padding(5.dp)
                                .width(35.dp)
                                .height(35.dp)
                                .align(alignment = Alignment.CenterVertically),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(Color.Transparent),
//                                border = BorderStroke(1.dp, Color.White)
                            ) {
                              Text(text = when(index){
                                           0->"A"
                                           1->"B"
                                           2->"C"
                                           3->"D"
                                         else -> {
                                             ""
                                         }
                              }
                                          ,
                                  modifier = Modifier
                                      .padding(0.dp)
                                      .align(alignment = Alignment.CenterHorizontally),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                       )
                            }
                            Text(text = annotatedString,
                                modifier = Modifier.padding(6.dp)
                            )

                        }

                    }
                }

            }

    }

}

@Composable
fun DrawDottedLine(pathEffect: PathEffect){
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)){
        drawLine(color = AppColors.mLightGray,
            start = Offset(0f,0f),
            end = Offset(size.width,y=0f),
            pathEffect = pathEffect)
    }
}

@Preview
@Composable
fun ShowProgress(score:Int=2){

    val gradient= Brush.linearGradient(
        listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))

    val progressFactor by remember (score){
        mutableStateOf(score*0.005f)
    }

    Row(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .height(15.dp)
        .border(
            width = 2.dp, brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mOffWhite, AppColors.mOffWhite
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomStartPercent = 50,
                bottomEndPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {

        Button(contentPadding= PaddingValues(1.dp),
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
        enabled = false,
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor=Color.Transparent,
            disabledContainerColor=Color.Transparent)
        ) {

          //Fix Score Display  TODO
//
//            Row() {
//                Text(text = (score * 2).toString(),
//                    modifier = Modifier
//                        .clip(shape = RoundedCornerShape(23.dp))
//                        .fillMaxHeight(0.87f)
//                        .fillMaxWidth()
//                        .padding(6.dp),
//                    color = AppColors.mOffWhite,
//                    textAlign = TextAlign.Center)
//            }

        }

    }
}

//@Preview
@Composable
fun QuestionTracker(counter:Int=10,
                    outOf:Int=100){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)){
                withStyle(style = SpanStyle(color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp)){

                    append("$counter/")

                    withStyle(style = SpanStyle(color = AppColors.mOffWhite,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp
                    )){
                        append("$outOf")
                    }
                }
            }
        },
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.Start))
    }

}


@Composable
fun Timer(
    currentTime:MutableState<Long>,
    timeRunning:(Boolean)->Unit,
    isTimeRunning:MutableState<Boolean>,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier=Modifier,
    initialValue:Float=1f,
    strokeWith: Dp =5.dp,
    navController:()->Unit
    ){

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var value by remember {
        mutableStateOf(initialValue)
    }

    var total by remember {
        mutableStateOf(currentTime.value)
    }




//    var isTimeRunning by remember {
//        mutableStateOf(timeStatus)
//    }
    //the key inside launchEffect means: Whenever the key changes, it will re run the code inside.
    LaunchedEffect(key1 = currentTime.value, key2 = isTimeRunning.value){

        if (currentTime.value>0 && isTimeRunning.value){
            delay(100L)
            currentTime.value -=100L
            value=currentTime.value/total.toFloat()
        }else{
            timeRunning(isTimeRunning.value)
            isTimeRunning.value=false
            navController.invoke()
        }

    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.onSizeChanged {
            size=it
        }
    ) {

        Canvas(modifier = modifier){
            //startAngle =  the starting angle,
            // sweepAngle = for how many degrees we want this to be long
            //useCenter will prevent the ends to be connected to the center. will test this latter

            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(),size.height.toFloat()),
                style = Stroke(strokeWith.toPx(), cap = StrokeCap.Round)//make sure that the ends are round.
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(size.width.toFloat(),size.height.toFloat()),
                style = Stroke(strokeWith.toPx(), cap = StrokeCap.Round)//make sure that the ends are round.
            )

            val center = Offset(size.width/2f,size.height/2f)   // gets the center
            val beta = (250f * value + 145f) * (PI /180f).toFloat() //the angle between our center and the handle.
            val r = size.width/2f
            val a = cos(beta) * r
            val b = sin(beta) * r

            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWith * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }

        Text(
            text="${(currentTime.value/1000L)}", //hour time is in millis and we want it in seconds
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

//        Button(onClick = {
//                currentTime=totalTime.value
//                isTimeRunning.value=true
//
//        },modifier=Modifier.align(Alignment.BottomCenter),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = if(!isTimeRunning.value || currentTime <= 0L){
//                    Color.Green
//                }else{
//                    Color.Red
//                }
//            )) {
//            Text(
//                text = if (isTimeRunning.value && currentTime >= 0L) "Stop"
//                else if (isTimeRunning.value && currentTime >= 20) "Start"
//                else "Restart"
//            )
//        }

    }

}