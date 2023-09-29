package com.example.moztrivia.util

import androidx.navigation.NavController
import kotlin.random.Random

object IndexTracker {

    fun generateRandomIndex(maxIndex: Int, selectedIndices: MutableSet<Int>,onEmptyIndice:()->Unit): Int {
        val remainingIndices = (0 until maxIndex).filter {
            it !in selectedIndices
        }
        return if (remainingIndices.isEmpty()) {
//            selectedIndices.clear()
            onEmptyIndice()
            Random.nextInt(0, maxIndex)
        } else {
            remainingIndices[Random.nextInt(0, remainingIndices.size)]
        }
    }
}