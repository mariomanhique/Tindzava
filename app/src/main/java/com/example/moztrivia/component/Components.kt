package com.example.moztrivia.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFields(placeHolder:String,
                value: String,
                textTransformation: VisualTransformation,
                onValueChanged: (String)->Unit){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .background(color= Color.White,shape = RoundedCornerShape(5.dp)),
            placeholder = {
                Text(text = placeHolder)
            }, colors = TextFieldDefaults.textFieldColors(Color.White),
            visualTransformation = textTransformation)
    }
}