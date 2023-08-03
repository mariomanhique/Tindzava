package com.example.moztrivia.widgets

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable

@Composable
fun HandleBackButtonPress(
    onBackPressed: () -> Unit
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current

    BackHandler(enabled = true) {
        onBackPressed.invoke()
    }

}