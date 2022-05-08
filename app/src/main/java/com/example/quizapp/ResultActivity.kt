package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra(Constants.USER_NAME)
        findViewById<TextView>(R.id.tvName).text = username

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_Questions, 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

        findViewById<TextView>(R.id.tvScore).text = "Your score is $correctAnswers out of $totalQuestions ."

        findViewById<Button>(R.id.btnFinish).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}