package com.example.quizify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.quizify.R
import com.google.firebase.auth.FirebaseAuth

class LoginIntro : AppCompatActivity() {
    lateinit var btngetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        btngetStarted=findViewById(R.id.btnGetStarted)

        btngetStarted.setOnClickListener {
            redirect("LOGIN")
        }
        val auth= FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            Toast.makeText(this, "User is already logged in", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
    }
    private fun redirect(name:String){
        val intent = when(name){
            "LOGIN"-> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}
