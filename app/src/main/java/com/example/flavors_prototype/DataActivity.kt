package com.example.flavors_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

//This portion of the code

class DataActivity : AppCompatActivity()
{
    //assigns the type of variables
    private  lateinit var  dbreference : DatabaseReference
    private  lateinit var  dataItemRecyclerView : RecyclerView
    private  lateinit var  dataArrayList: ArrayList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?)
    {
        //use layout from activity_data
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
        //call the nodes that exist under countries
        dbreference = FirebaseDatabase.getInstance().getReference("countries")
        dbreference.addValueEventListener(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                //if there is data do the following
                if (snapshot.exists()){
                    //loop through children in data retrieved
                    for (countrySnapshot in snapshot.children){

                        //every iteration, add the add data from child to array element
                        val dataItem = countrySnapshot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null

                    }
                    //give the recycler and adapter to parse the data in the array created above
                    dataItemRecyclerView.adapter = MyAdapter(dataArrayList)


                }
            }

            //need to write some error handling in case there is no data
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}