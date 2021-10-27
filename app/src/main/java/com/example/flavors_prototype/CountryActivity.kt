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
        rvCountry.setHasFixedSize(true) //Set all sizes to be equal for better performance

        val countryList = getListofCountry()
        val countryAdapter = CustomAdapter(countryList)
        rvCountry.adapter = countryAdapter
        //Set RecyclerView's layout manager eqyal to LinearLayoutManager
        rvCountry.layoutManager = LinearLayoutManager(this)


    }

    private fun getListofCountry(): MutableList<String> {

        //HARDCODE
        val data = mutableListOf<String>()
        data.add("India")
        data.add("Italy")
        data.add("Mexico")

        return data
    }
}