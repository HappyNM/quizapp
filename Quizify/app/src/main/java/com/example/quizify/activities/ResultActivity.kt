package com.example.quizify.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.example.quizify.R
import com.example.quizify.models.Question
import com.example.quizify.models.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    lateinit var txtScore: TextView
    lateinit var txtAnswer: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        txtAnswer=findViewById(R.id.txtAnswer)
        txtScore=findViewById(R.id.txtScore)
        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        setAnswerView()
    }

    private fun setAnswerView() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        var score= 0
        for (entry:MutableMap.MutableEntry<String,Question> in quiz.questions.entries){
            val question=entry.value
            if(question.answer==question.userAnswer){
                score+=10
            }
        }
        txtScore.text= "Your Score : $score"

    }
}