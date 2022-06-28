package com.example.wizard.uiux

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.wizard.R
import com.example.wizard.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_results)

        supportActionBar?.hide()
        setContentView(R.layout.activity_results)

        // Set name on result page
        binding.tvNameResult.text = "Congratulations $name"
        binding.tvScore.text = "Your score is $score/10"

        binding.btnFinish.setOnClickListener {
            // Reset the score when the game finishes
            score = 0

            // Go to homepage
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java,
                )
            )

        }

    }
}