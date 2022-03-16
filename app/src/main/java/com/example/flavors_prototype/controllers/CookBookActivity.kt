package com.example.flavors_prototype.controllers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.R
import com.example.flavors_prototype.models.Ingredient
import com.example.flavors_prototype.models.Recipe
import com.example.flavors_prototype.views.cookBookAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CookBookActivity : AppCompatActivity()
{
    private  lateinit var  dbreference : DatabaseReference//reference to parent node
    private  lateinit var  dataItemRecyclerView : RecyclerView//recycler object
    private  lateinit var  dataArrayList: ArrayList<Recipe>//array of recipes
    private  lateinit var  recipeIngredients: ArrayList<ArrayList<Ingredient>>//array of ingredients

    //receives current context in Bundle
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //uses method from Bundle to bind to the correct xlm file
        setContentView(R.layout.activity_cook_book)

        //array of arrays because multiple values in Ingredient
        recipeIngredients = arrayListOf<ArrayList<Ingredient>>()

        //binds recycler view to correct portion of xml file
        dataItemRecyclerView = findViewById(R.id.dishList)
        //binds layout manager with context
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        //fixes window screen when items deleted
        dataItemRecyclerView.setHasFixedSize(true)
        //receives array of recipes
        dataArrayList = arrayListOf<Recipe>()
        getRecipeData()//calls data
    }
    //retrieves data from firebase
    private fun getRecipeData()
    {
        //current user id
        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        //bookmarked recipes stored in node Likes, this references the current user in Likes
        dbreference = FirebaseDatabase.getInstance().getReference("Likes").child(currentUserID)

        //listener for data changes on Likes
        dbreference.addValueEventListener(object : ValueEventListener {
            //when data changes take snapshot
            override fun onDataChange(snapshot: DataSnapshot) {
                //if data exists
                if (snapshot.exists()){
                    //loop through children in Likes
                    for (recipeSnapShot in snapshot.children){

                        /* prepare 1D array to hold this recipes ingredients */
                        val tempArray : ArrayList<Ingredient> = arrayListOf<Ingredient>()

                        /* Probe ingredients node of the dish */
                        val ingredientNode = recipeSnapShot.child("Ingredients")

                        /* collect ingredients */
                        for (ingredient in ingredientNode.children){

                            val dataItem = ingredient.getValue(Ingredient::class.java)
                            tempArray.add(dataItem!!)// !! checks that object is not null
                        }

                        /* store ingredients for this recipe */
                        /* this 2d array will be passed to the adapter after ingredients for each
                         * recipe is collected */
                        recipeIngredients.add(tempArray)

                        //uses Recipe model to extract data from current snapshot
                        val dataItem = recipeSnapShot.getValue(Recipe::class.java)

                        //push data to array that will be used in recycler view
                        dataArrayList.add(dataItem!!)// !! checks that object is not null
                   }
                    //calls recycler view with the array of recipes and ingredients
                    dataItemRecyclerView.adapter = cookBookAdapter(dataArrayList, recipeIngredients, this@CookBookActivity)
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
            startActivity(Intent(this, CookBookActivity::class.java))
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
