package com.mad43.recipestask.utils


fun setDifficulty(difficultyNumber: Int): String {
    return when (difficultyNumber) {
        0 -> "Easy"
        1 -> "Casual"
        else -> "Challenging"
    }
}

