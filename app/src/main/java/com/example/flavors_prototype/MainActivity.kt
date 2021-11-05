package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var dataBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBtn = findViewById(R.id.recyclerviewbtn)

        dataBtn.setOnClickListener {
            val pullButton = Intent(this, CountryActivity::class.java)
            startActivity(pullButton)
            finish()
        }
    }
}