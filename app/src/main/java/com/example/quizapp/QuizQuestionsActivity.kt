package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int =0
    private var mUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUsername = intent.getStringExtra(Constants.USER_NAME)
        mQuestionsList = Constants.getQuestions()

        setQuestion()


        findViewById<TextView>(R.id.tvOptionOne).setOnClickListener(this)
        findViewById<TextView>(R.id.tvOptionTwo).setOnClickListener(this)
        findViewById<TextView>(R.id.tvOptionThree).setOnClickListener(this)
        findViewById<TextView>(R.id.tvOptionFour).setOnClickListener(this)
        findViewById<Button>(R.id.btnSubmit).setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun setQuestion(){
        val question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size){
            findViewById<Button>(R.id.btnSubmit).text = "finish"
        }else{
            findViewById<Button>(R.id.btnSubmit).text = "SUBMIT"
        }

        findViewById<ProgressBar>(R.id.progressBar).progress = mCurrentPosition

        findViewById<TextView>(R.id.tvProgress).text = "$mCurrentPosition" + "/" + findViewById<ProgressBar>(R.id.progressBar).max

        findViewById<TextView>(R.id.tvQuestion).text = question.question
        findViewById<ImageView>(R.id.ivImage).setImageResource(question.image)
        findViewById<TextView>(R.id.tvOptionOne).text = question.optionOne
        findViewById<TextView>(R.id.tvOptionTwo).text = question.optionTwo
        findViewById<TextView>(R.id.tvOptionThree).text = question.optionThree
        findViewById<TextView>(R.id.tvOptionFour).text = question.optionForth


    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, findViewById<TextView>(R.id.tvOptionOne))
        options.add(1, findViewById<TextView>(R.id.tvOptionTwo))
        options.add(2, findViewById<TextView>(R.id.tvOptionThree))
        options.add(3, findViewById<TextView>(R.id.tvOptionFour))

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvOptionOne -> {
                selectedOptionView(findViewById<TextView>(R.id.tvOptionOne), 1)
            }
            R.id.tvOptionTwo -> {
                selectedOptionView(findViewById<TextView>(R.id.tvOptionTwo), 2)
            }
            R.id.tvOptionThree -> {
                selectedOptionView(findViewById<TextView>(R.id.tvOptionThree), 3)
            }
            R.id.tvOptionFour -> {
                selectedOptionView(findViewById<TextView>(R.id.tvOptionFour), 4)
            }
            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_Questions, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }

                    // This is for correct answer
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        findViewById<Button>(R.id.btnSubmit).text = "FINISH"
                    } else {
                        findViewById<Button>(R.id.btnSubmit).text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private  fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                findViewById<TextView>(R.id.tvOptionOne).background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                findViewById<TextView>(R.id.tvOptionTwo).background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                findViewById<TextView>(R.id.tvOptionThree).background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                findViewById<TextView>(R.id.tvOptionFour).background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }


    private  fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}