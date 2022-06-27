package com.example.wizard.uiux

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.wizard.R
import com.example.wizard.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var name = ""
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.btnStartQuiz.setOnClickListener {
            if (binding.tvName.text.toString().isEmpty()) {
                binding.tvNameParent.error = "Please provide a name"
            } else {
                name = binding.tvName.text.toString()
                startActivity(
                    Intent(
                        this,
                        ChooseDifficulty::class.java,
                    )
                )
            }
        }
    }
}