package com.example.wizard.uiux

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.wizard.R
import com.example.wizard.databinding.ActivityChooseDifficultyBinding


class ChooseDifficulty : AppCompatActivity() {

    private lateinit var binding: ActivityChooseDifficultyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_difficulty)


        binding.easyButton.setOnClickListener {
            startNewActivity("easy")
        }

        binding.mediumButton.setOnClickListener {
            startNewActivity("medium")
        }

        binding.hardButton.setOnClickListener {
            startNewActivity("hard")
        }
    }

    private fun startNewActivity(s: String) {
        val bundle = Bundle()
        bundle.putString("level", s)

        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}