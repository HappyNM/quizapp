package com.example.quizify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizify.R
import com.example.quizify.adapters.QuizAdapter
import com.example.quizify.models.Quiz
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var btnDatePicker: FloatingActionButton
    lateinit var firestore: FirebaseFirestore
    private lateinit var quizRecyclerView:RecyclerView
    lateinit var topAppBar:MaterialToolbar
    lateinit var mainDrawer:DrawerLayout
    lateinit var navigationView:NavigationView
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDatePicker=findViewById(R.id.btnDatePicker)
        quizRecyclerView=findViewById(R.id.quizRecyclerView)
        setUpViews()
        topAppBar=findViewById(R.id.topAppBar)
        mainDrawer=findViewById(R.id.mainDrawer)
        navigationView=findViewById(R.id.navigationView)

        topAppBar.setNavigationOnClickListener {
            mainDrawer.open()
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.rateus -> {
                    val intent = Intent(this, RateActivity::class.java)
                    startActivity(intent)
                }

                }

            menuItem.isChecked = true
            mainDrawer.close()
            true
        }

    }



    private fun setUpViews() {
        setUpRecyclerView()
        setUpFireStore()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter= SimpleDateFormat("dd-MM-yyyy")
                val date= dateFormatter.format(Date(it))
                val intent=Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }

    }

    private fun setUpRecyclerView() {
        adapter= QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        quizRecyclerView.adapter = adapter

    }

}