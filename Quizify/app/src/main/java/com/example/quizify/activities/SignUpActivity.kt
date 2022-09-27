package com.example.quizify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizify.R
import com.example.quizify.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var edtEmailAddress:EditText
    lateinit var edtPassword:EditText
    lateinit var edtConfPassword:EditText
    lateinit var btnSignUp:Button
    lateinit var txtViewLogin:TextView

    lateinit var firebaseAuth: FirebaseAuth
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
            edtEmailAddress=findViewById(R.id.etEmailAddress)
            edtPassword=findViewById(R.id.etPassword)
            edtConfPassword=findViewById(R.id.etconfPassword)
            btnSignUp=findViewById(R.id.btnSignUp)
            txtViewLogin=findViewById(R.id.txtViewLogin)

            firebaseAuth=Firebase.auth

            btnSignUp.setOnClickListener {
                signUpUser()
            }
            txtViewLogin.setOnClickListener {
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

    }
    private fun signUpUser(){
        val email: String = edtEmailAddress.text.toString()
        val Password: String =edtPassword.text.toString()
        val confPassword: String =edtConfPassword.text.toString()
        if (email.isBlank()||Password.isBlank()||confPassword.isBlank()){
            Toast.makeText(this,"Email and password cant be blank",Toast.LENGTH_LONG).show()

        }else if(Password != confPassword){
            Toast.makeText(this,"Password do not match",Toast.LENGTH_LONG).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Signed successfully",Toast.LENGTH_LONG).show()
                val intent= Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Failed to create",Toast.LENGTH_LONG).show()
            }
        }

    }
}