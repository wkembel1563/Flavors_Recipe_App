package com.example.flavors_prototype

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class cookBookActivity : AppCompatActivity()
{
    private  lateinit var  dbreference : DatabaseReference
    private  lateinit var  dataItemRecyclerView : RecyclerView
    private  lateinit var  dataArrayList: ArrayList<Recipe>
    private  lateinit var  recipeIngredients: ArrayList<ArrayList<Ingredient>>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cook_book)//activity_data

        recipeIngredients = arrayListOf<ArrayList<Ingredient>>()

        dataItemRecyclerView = findViewById(R.id.dishList)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)
        dataArrayList = arrayListOf<Recipe>()
        getRecipeData()

    }
    //retrieves data from firebase
    private fun getRecipeData()
    {

        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        dbreference = FirebaseDatabase.getInstance().getReference("Likes").child(currentUserID)
        dbreference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (recipeSnapShot in snapshot.children){

                        /* prepare 1D array to hold this recipes ingredients */
                        val tempArray : ArrayList<Ingredient> = arrayListOf<Ingredient>()

                        /* Probe ingredients node of the dish */
                        val ingredNode = recipeSnapShot.child("Ingredients")

                        /* collect ingredients */
                        for (ingredient in ingredNode.children){

                            val dataItem = ingredient.getValue(Ingredient::class.java)
                            tempArray.add(dataItem!!)// !! checks that object is not null

                        }

                        /* store ingredients for this recipe */
                        /* this 2d array will be passed to the adapter after ingredients for each
                         * recipe is collected */
                        recipeIngredients.add(tempArray)

                        val dataItem = recipeSnapShot.getValue(Recipe::class.java)
                        dataArrayList.add(dataItem!!)// !! checks that object is not null

                   }
                    dataItemRecyclerView.adapter = cookBookAdapter(dataArrayList, recipeIngredients, this@cookBookActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    //function for main menu bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater : MenuInflater = menuInflater
        with(inflater) {
            inflate(R.menu.main_menu,menu)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if(item.itemId == R.id.cook_book)
        {
            startActivity(Intent(this, cookBookActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.country_selection)
        {
            startActivity(Intent(this, CountryActivity::class.java))
            //return true
        }


        if(item.itemId == R.id.shoppingList_selection)
        {
            startActivity(Intent(this, ShoppingListActivity::class.java))
            //return true
        }

        if(item.itemId == R.id.dish_recommendation)
        {
            startActivity(Intent(this, RecommendationActivity::class.java))
            //return true
        }


        return super.onOptionsItemSelected(item)
    }
}
