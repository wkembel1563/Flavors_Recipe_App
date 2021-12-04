package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


// TODO: sort recommended dishes
// TODO: merge carlos dishview layout with yours

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        startActivity(Intent(this, LoginActivity::class.java))
    }
}