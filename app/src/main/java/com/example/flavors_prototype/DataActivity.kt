package com.example.flavors_prototype

import android.content.Intent
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
                      // Dummy Item for Header
//                    val dummy = Recipe("", "", "", "", "", "")
//                    dataArrayList.add(dummy)

                    for (countrySnapshot in snapshot.children){
                        val dataItem = countrySnapshot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null

                    }

                    //go to DishView Activity on click
                    var adapter = MyAdapter(dataArrayList)
                    dataItemRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            // when a card is clicked, go to DishView
                            val intent = Intent(this@DataActivity, DishViewActivity::class.java)

                            // pass dish data to DishView
                            intent.putExtra("dish_Place", dataArrayList[position].Place)
                            intent.putExtra("dish_Recipe", dataArrayList[position].Recipe)
                            intent.putExtra("dish_CookTime", dataArrayList[position].CookTime)
                            intent.putExtra("dish_PrepTime", dataArrayList[position].PrepTime)
                            intent.putExtra("dish_Instructions", dataArrayList[position].Instructions)
                            intent.putExtra("dish_Ingredients", dataArrayList[position].Ingredients)

                            // begin DishView
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}