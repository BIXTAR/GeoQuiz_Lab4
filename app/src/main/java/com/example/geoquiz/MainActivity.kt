package com.example.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val questionBank = listOf(
        Question(R.string.question_1, false),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, true),
        Question(R.string.question_5, false)
    )

    private var currentIndex = 0
    private var correctAnswers = 0
    private var answeredQuestions = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.question_text_view)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        currentIndex = savedInstanceState?.getInt("index") ?: 0
        correctAnswers = savedInstanceState?.getInt("correct") ?: 0
        answeredQuestions = savedInstanceState?.getIntegerArrayList("answered")?.toMutableSet() ?: mutableSetOf()

        updateQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex++
            updateQuestion()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("index", currentIndex)
        outState.putInt("correct", correctAnswers)
        outState.putIntegerArrayList("answered", ArrayList(answeredQuestions))
    }

    private fun updateQuestion() {
        questionTextView.setText(questionBank[currentIndex].textResId)

        if (answeredQuestions.contains(currentIndex)) {
            trueButton.visibility = View.INVISIBLE
            falseButton.visibility = View.INVISIBLE
        } else {
            trueButton.visibility = View.VISIBLE
            falseButton.visibility = View.VISIBLE
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }

        if (currentIndex >= questionBank.size - 1) {
            nextButton.isEnabled = false
            nextButton.visibility = View.INVISIBLE

            Toast.makeText(
                this,
                "Правильных ответов: $correctAnswers из ${questionBank.size}",
                Toast.LENGTH_LONG
            ).show()
        } else {
            nextButton.isEnabled = true
            nextButton.visibility = View.VISIBLE
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            correctAnswers++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        trueButton.isEnabled = false
        falseButton.isEnabled = false
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        answeredQuestions.add(currentIndex)
    }
}