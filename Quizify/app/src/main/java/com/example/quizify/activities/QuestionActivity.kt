package com.example.quizify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizify.R
import com.example.quizify.adapters.OptionAdapter
import com.example.quizify.models.Question
import com.example.quizify.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.w3c.dom.Text

class QuestionActivity : AppCompatActivity() {
    lateinit var btnPrevious: Button
    lateinit var btnNext: Button
    lateinit var btnSubmit: Button
    var quizzes : MutableList<Quiz>?= null
    var questions : MutableMap<String, Question>?= null
    var index = 1
    lateinit var description:TextView
    lateinit var optionList:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        btnNext=findViewById(R.id.btnNext)
        btnSubmit=findViewById(R.id.btnSubmit)
        btnPrevious=findViewById(R.id.btnPrevious)
        optionList=findViewById(R.id.optionList)

        description=findViewById(R.id.description)
        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        btnNext.setOnClickListener {
            index++
            bindViews()
        }
        btnSubmit.setOnClickListener {
            Log.d("FINAL QUIZ",questions.toString())
            val intent= Intent(this, ResultActivity::class.java)
            val json= Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)

        }
    }

    private fun setUpFireStore() {
        val firestore= FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if(date != null){
            firestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty){
                        quizzes=it.toObjects(Quiz::class.java)
                        questions= quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        btnPrevious.visibility= View.GONE
        btnNext.visibility= View.GONE
        btnSubmit.visibility= View.GONE
        if (index==1){
            btnNext.visibility= View.VISIBLE
        }else{
            if (index== questions!!.size){
                btnPrevious.visibility= View.VISIBLE
                btnSubmit.visibility= View.VISIBLE
            }else{
                btnPrevious.visibility= View.VISIBLE
                btnNext.visibility= View.VISIBLE
            }
        }
        val question = questions!!["question$index"]
        question?.let {
            description.text= it.description
            val optionAdapter= OptionAdapter(this,it)
            optionList.layoutManager=LinearLayoutManager(this)
            optionList.adapter=optionAdapter
            optionList.setHasFixedSize(true)
        }


    }
}