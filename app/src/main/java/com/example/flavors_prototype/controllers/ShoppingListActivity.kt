package com.example.flavors_prototype.controllers

//import android.R

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.models.Ingredient
import com.example.flavors_prototype.R
import com.example.flavors_prototype.views.ShopListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ShoppingListActivity : AppCompatActivity() {


    private  lateinit var  shopListItemRecyclerView : RecyclerView
    private  lateinit var  recipeIngredients: ArrayList<ArrayList<Ingredient>>
    private  lateinit var  recipeNames: ArrayList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoplistholder)
        title = "Shopping List...";
        // this will be from the shoplistholder.xml
        shopListItemRecyclerView= findViewById(R.id.shopList)
        shopListItemRecyclerView.layoutManager = LinearLayoutManager(this)
        shopListItemRecyclerView.setHasFixedSize(true)

        recipeIngredients = arrayListOf<ArrayList<Ingredient>>()
        recipeNames = arrayListOf<String>()

        //pass the database reference here as a parameter or make the call for the reference inside the functio
        getShoppingListData()

    }

    private fun getShoppingListData()
    {

        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        lateinit var  dbreference : DatabaseReference

        //you want the shopping lists for the specific user using the app, give the currentuser it look for that child
        dbreference = FirebaseDatabase.getInstance().getReference()
            .child("ShoppingList")
            .child(currentUserID)
        dbreference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    /* get each recipe that has shopping list items */
                    for (recipe in snapshot.children){

                        /* keep name of recipe to pass to adapter */
                        val recipeName : String = recipe.key.toString()
                        recipeNames.add(recipeName)

                        /* prepare 1D array to hold this recipes ingredients */
                        val tempArray : ArrayList<Ingredient> = arrayListOf<Ingredient>()

                        /* collect ingredients for the dish */
                        for (ingredient in recipe.children){

                            /* collect ingredients of the recipe */
                            val dataItem = ingredient.getValue(Ingredient::class.java)
                            tempArray.add(dataItem!!)// !! checks that object is not null

                        }

                        /* store ingredients for this recipe */
                        /* this 2d array will be passed to the adapter after ingredients for each
                         * recipe is collected */
                        recipeIngredients.add(tempArray)
                    }

                    shopListItemRecyclerView.adapter = ShopListAdapter(recipeIngredients, recipeNames, this@ShoppingListActivity)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

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
            Toast.makeText(this, "Cookbook", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CookBookActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.country_selection)
        {   Toast.makeText(this, "Select a Country", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CountryActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.dish_recommendation)
        {
            Toast.makeText(this, "Recommendations", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RecommendationActivity::class.java))
            //return true
        }
        if(item.itemId == R.id.shoppingList_selection)
        {
            Toast.makeText(this, "Shopping List", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ShoppingListActivity::class.java))
            //return true
        }

        return super.onOptionsItemSelected(item)
    }

}

