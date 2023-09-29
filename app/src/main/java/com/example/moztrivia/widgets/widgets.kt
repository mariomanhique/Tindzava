package com.example.moztrivia.widgets

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable

@Composable
fun HandleBackButtonPress(
    onBackPressed: () -> Unit
) {
    BackHandler(enabled = true) {
        onBackPressed.invoke()
    }

}