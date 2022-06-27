package com.example.wizard.uiux

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.wizard.R
import com.example.wizard.databinding.ActivityQuestionBinding


var score = 0
class QuestionActivity : AppCompatActivity() {

    private var currentQuestionId = -1
    private var selectedAnswers = mutableMapOf<Int, String>()
    private lateinit var level: String
    private val originalOptionTextColor = Color.parseColor("#4A4A4A")

    private lateinit var binding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_question)

        binding.progressBar2.visibility = View.VISIBLE
        val bundle = intent.extras
        if (bundle != null){
            level = bundle.getString("level")!!
        }


        when(level){

        }
        binding.progressBar2.visibility = View.GONE

        val allOptions = arrayListOf(binding.tvOption1, binding.tvOption2, binding.tvOption3, binding.tvOption4)
        val questions: ArrayList<Question> = getQuestions()

        fun changeQuestion() {
            // Go to results screen if it's the end of questions Array
            if (currentQuestionId + 1 == questions.size) {
                return startActivity(
                    Intent(
                        this,
                        ResultsActivity::class.java,
                    )
                )
            }
            currentQuestionId += 1

            val question = questions[currentQuestionId]

            binding.tvQuestion.text = question.text
            binding.progressBar.progress = currentQuestionId

            binding.tvOption1.text = question.option1
            binding.tvOption2.text = question.option2
            binding.tvOption3.text = question.option3
            binding.tvOption4.text = question.option4
        }

        fun resetOptionsColor() {
            for (option in allOptions) {
                option.setTextColor(originalOptionTextColor)
                option.typeface = Typeface.DEFAULT
                option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.default_option_border
                )
            }
        }

        // Add color changing listener in all options
        for (option in allOptions) {
            option.setOnClickListener {
                resetOptionsColor() // To prevent multi-selection

                option.setTextColor(Color.parseColor("#417EFF"))
                option.background = ContextCompat.getDrawable(
                    this,
                    R.drawable.selected_option_border
                )
                selectedAnswers[currentQuestionId] = option.text.toString()
            }
        }
        // Initial question when user presses "Start quiz"
        changeQuestion()

        binding.btnAnswerSubmit.setOnClickListener {
            if (selectedAnswers.containsKey(currentQuestionId)) {
                // If this is the last question, calculate score
                if (currentQuestionId + 1 == questions.size) {
                    for ((questionIndex, answer) in selectedAnswers) {
                        println("${questionIndex.toString()} ${answer.toString()}")
                        if (questions[questionIndex].correctAnswer == answer) {
                            score += 1
                        }
                    }
                }
                // Change question and options
                changeQuestion()
                resetOptionsColor()
            }
        }
    }
}