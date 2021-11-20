package com.example.flavors_prototype

//import android.R

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class ShoppingListActivity : AppCompatActivity() {


    private  lateinit var  dbreference : DatabaseReference
    private  lateinit var  getReference : DataSnapshot
    private  lateinit var  shopListItemRecyclerView : RecyclerView
    private  lateinit var  dataArrayList: ArrayList<Recipe>

    private  lateinit var  listItem : Recipe



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoplistholder)

        ///this will be the from the shoplisthold.xml
        shopListItemRecyclerView= findViewById(R.id.shopList)
        shopListItemRecyclerView.layoutManager = LinearLayoutManager(this)
        shopListItemRecyclerView.setHasFixedSize(true)

        dataArrayList = arrayListOf<Recipe>()

        //pass the database reference here as a parameter or make the call for the reference inside the functio
        getShoppingListData()

    }

    private fun getShoppingListData()
    {



        val currentUserID : String = "eIE3h1iriEbTtZugWI5L1MZswBk1"
        lateinit var  dbreference : DatabaseReference
        lateinit var  getReference : DataSnapshot


        //this is where you will be looking for information about likes , still look on tree with countries and recipes but create method above to retrieve the names of what you need first

        //you want the shopping lists for the specific user using the app, give the currentuser it look for that child
        dbreference = FirebaseDatabase.getInstance().getReference().child("ShoppingList").child(currentUserID)
        dbreference.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (listSnapshot in snapshot.children){
                        val countryName = snapshot.value.toString()
                        val dataItem = listSnapshot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null
                    }

                    shopListItemRecyclerView.adapter = ShopListAdapter(dataArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}

