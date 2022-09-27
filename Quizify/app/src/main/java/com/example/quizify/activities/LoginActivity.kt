package com.example.quizify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizify.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    lateinit var btnLogin:Button
    lateinit var txtViewSignup:TextView
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etLEmailAddress)
        etPassword=findViewById(R.id.etLPassword)
        btnLogin=findViewById(R.id.btnLogin)
        txtViewSignup=findViewById(R.id.txtViewSignUp)
        auth= FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            login()

        }
        txtViewSignup.setOnClickListener {
            val intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPassword.text.toString()
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
        }
    }
}