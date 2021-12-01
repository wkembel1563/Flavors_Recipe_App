package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
        val countryName = intent.extras?.get("country_name").toString()
        // TODO: get path from country view, and place in here
        // for now use arbitrary single country to populate
        dbreference = FirebaseDatabase.getInstance().getReference().child("kembel_test_tree").child(countryName)
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
                    var adapter = DishAdapter(dataArrayList)/////////////////////////////////////////////
                    dataItemRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : DishAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            // when a card is clicked, go to DishView
                            val intent = Intent(this@DataActivity, DishViewActivity::class.java)

                            // pass dish data to DishView
                            intent.putExtra("dish_Place", dataArrayList[position].Place)
                            intent.putExtra("dish_Recipe", dataArrayList[position].Recipe)
                            intent.putExtra("dish_CookTime", dataArrayList[position].CookTime)
                            intent.putExtra("dish_PrepTime", dataArrayList[position].PrepTime)
                            intent.putExtra("dish_Instructions", dataArrayList[position].Instructions)
                            intent.putExtra("dish_Ingredient1", dataArrayList[position].Ingredient1)
                            intent.putExtra("dish_Ingredient2", dataArrayList[position].Ingredient2)
                            intent.putExtra("dish_Ingredient3", dataArrayList[position].Ingredient3)
                            intent.putExtra("dish_Ingredient4", dataArrayList[position].Ingredient4)
                            intent.putExtra("dish_Ingredient5", dataArrayList[position].Ingredient5)
                            intent.putExtra("dish_Ingredient6", dataArrayList[position].Ingredient6)
                            intent.putExtra("dish_Ingredient7", dataArrayList[position].Ingredient7)
                            intent.putExtra("dish_Ingredient8", dataArrayList[position].Ingredient8)
                            intent.putExtra("dish_Ingredient9", dataArrayList[position].Ingredient9)

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
    //creates menu bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        with(inflater) {
            inflate(R.menu.main_menu,menu)
        }
        return true
    }
    //when user selects option from menu, start that activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if(item.itemId == R.id.cook_book)
        {
            startActivity(Intent(this, cookBookActivity::class.java))
            return true
        }
        if(item.itemId == R.id.country_selection)
        {

            startActivity(Intent(this, CountryActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}