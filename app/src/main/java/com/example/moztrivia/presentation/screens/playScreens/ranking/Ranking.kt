package com.example.moztrivia.presentation.screens.playScreens.ranking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moztrivia.R

@Composable
fun PlayOnline (navController: NavController){

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            )

    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            LazyColumn(){
                items(10){
                    UserCard()
                }
            }
    }

}

}


@Composable
fun UserCard(){
    Card(
        Modifier
            .padding(start = 25.dp, end = 25.dp, top = 4.dp)
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Card(shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription ="",
                    modifier = Modifier.size(80.dp)
                )
            }


            Text(text = "Sparta")

            Text(text = "20093")
        }

    }
}