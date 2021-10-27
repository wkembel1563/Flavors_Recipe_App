package com.example.flavors_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView




class CountryActivity : AppCompatActivity() {

    lateinit var rvCountry: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    var country: ArrayList<Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        rvCountry = findViewById(R.id.rvCountry)
        //HARDCODE
        val data = arrayListOf<String>()
        data.add("India")
        data.add("Italy")
        data.add("Mexico")


        val adapter = RecyclerViewAdapter(this, data)
        linearLayoutManager = LinearLayoutManager(this)
        rvCountry.layoutManager = linearLayoutManager


    }
}