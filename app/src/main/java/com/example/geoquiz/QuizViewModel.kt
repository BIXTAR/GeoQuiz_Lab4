package com.example.geoquiz.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import com.example.geoquiz.model.Question

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_oceans, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_europe, false),
    )

    var currentIndex = 0
    var correctAnswers = 0
    val hasAnswered = BooleanArray(questionBank.size) { false }
    var cheatTokensUsed = 0

    val currentQuestion: Question
        get() = questionBank[currentIndex]

    val isLastQuestion: Boolean
        get() = currentIndex == questionBank.size - 1

    fun moveToNext() {
        if (currentIndex < questionBank.size - 1) currentIndex++
    }

    fun reset() {
        currentIndex = 0
        correctAnswers = 0
        cheatTokensUsed = 0
        for (i in hasAnswered.indices) hasAnswered[i] = false
    }
}