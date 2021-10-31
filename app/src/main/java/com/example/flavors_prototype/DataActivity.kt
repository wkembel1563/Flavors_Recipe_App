package com.example.flavors_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DataActivity : AppCompatActivity()
{


    private  lateinit var  dbreference : DatabaseReference
    private  lateinit var  dataItemRecyclerView : RecyclerView
    private  lateinit var  dataArrayList: ArrayList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        dataItemRecyclerView = findViewById(R.id.dataList)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)

        dataArrayList = arrayListOf<Recipe>()
        getRecipeData()

    }
    //retrieves data from firebase
    private fun getRecipeData()
    {
        // TODO: get path from country view, and place in here
        // for now use arbitrary single country to populate
        dbreference = FirebaseDatabase.getInstance().getReference().child("kembel_test_tree").child("USA")
        dbreference.addValueEventListener(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    for (countrySnapshot in snapshot.children){


                        val dataItem = countrySnapshot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null

                    }

                    dataItemRecyclerView.adapter = MyAdapter(dataArrayList)


                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}