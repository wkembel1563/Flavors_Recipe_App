package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    // test
    //initializing a button for main screen
    lateinit var dataBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //binds button to activity_main by id
        dataBtn = findViewById(R.id.recyclerviewbtn)

        //when button is clicked, call DataActivity class to retrieve data
        dataBtn.setOnClickListener {

            val pullButton = Intent(this, DataActivity::class.java)
            startActivity(pullButton)
            finish()
        }

    }
}