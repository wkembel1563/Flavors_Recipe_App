package com.example.flavors_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseError

import androidx.annotation.NonNull

import com.google.firebase.database.ValueEventListener

//import android.R

import android.view.View

import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager


class ShoppingListActivity : AppCompatActivity() {

    /*
    private  lateinit var  dbreference : DatabaseReference
    private  lateinit var  getReference : DataSnapshot
    private  lateinit var  dataItemRecyclerView : RecyclerView
    private  lateinit var  dataArrayList: ArrayList<Recipe>

    private  lateinit var  dishItem : Recipe
    val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        /*
        dataItemRecyclerView = findViewById(R.id.dataList)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)

        dataArrayList = arrayListOf<Recipe>()
        getRecipeData()
        */

    }}

    /*
    private fun getRecipeData()
    {

        //this is where you will be looking for information about likes , still look on tree with countries and recipes but create method above to retrieve the names of what you need first

        dbreference = FirebaseDatabase.getInstance().getReference().child("ShoppingList")
        dbreference.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (countrySnapshot in snapshot.children){
                        val dataItem = countrySnapshot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null
                    }

                    dataItemRecyclerView.adapter = cookBookAdapter(dataArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}
*/
