package com.example.wizard.uiux

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.wizard.R
import com.example.wizard.api.APIInterface
import com.example.wizard.databinding.ActivityQuestionBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var score = 0
class QuestionActivity : AppCompatActivity() {

    private val BASE_URL = "https://opentdb.com/api.php?"
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
        val rounds = getQuestions(level)
        binding.progressBar2.visibility = View.GONE

        val allOptions = arrayListOf(binding.tvOption1, binding.tvOption2, binding.tvOption3, binding.tvOption4)
        //val questions: ArrayList<Result> = getQuestions()

        fun changeQuestion() {
            // Go to results screen if it's the end of questions Array
            if (currentQuestionId + 1 == rounds.size) {
                return startActivity(
                    Intent(
                        this,
                        ResultsActivity::class.java,
                    )
                )
            }
            currentQuestionId += 1

            val question = rounds[currentQuestionId].question

            binding.tvQuestion.text = question
            binding.progressBar.progress = currentQuestionId
            val newCor = (0..3).shuffled().last()
            var curr = 0
            val opts = arrayListOf(binding.tvOption1, binding.tvOption2, binding.tvOption3, binding.tvOption4)

            for(opt in opts.indices){
                opts[opt].text = rounds[currentQuestionId].incorrect_answers[curr]
                ++curr
                if(newCor == opt)
                {
                    opts[opt].text = rounds[currentQuestionId].correct_answer
                    --curr
                }
            }

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
                if (currentQuestionId + 1 == rounds.size) {
                    for ((questionIndex, answer) in selectedAnswers) {
                        println("$questionIndex $answer")
                        if (rounds[questionIndex].correct_answer == answer) {
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

    private fun getQuestions(level: String): List<com.example.wizard.dataClasses.Result> {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        var data = retrofitBuilder.getDataEasy()
        when(level){
            "easy" -> data = retrofitBuilder.getDataEasy()
            "medium" -> data = retrofitBuilder.getDataMedium()
            "hard" -> data = retrofitBuilder.getDataHard()
            else -> {
                Toast.makeText(this,
                    "Something is not right...!!!, contact the developer",
                    Toast.LENGTH_SHORT).show()
                startNewActivity()
            }
        }



        return data.results
    }

    private fun startNewActivity() {
        val intent = Intent(this, ChooseDifficulty::class.java)
        startActivity(intent)
    }
}