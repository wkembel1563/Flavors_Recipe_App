package com.example.flavors_prototype

//import android.R

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ShoppingListActivity : AppCompatActivity() {


//jj    private  lateinit var  dbreference : DatabaseReference
//    private  lateinit var  getReference : DataSnapshot
    private  lateinit var  shopListItemRecyclerView : RecyclerView
//    private  lateinit var  shopListIngredRecyclerView : RecyclerView
    private  lateinit var  recipeIngredients: ArrayList<ArrayList<Ingredient>>
    private  lateinit var  recipeNames: ArrayList<String>
    private  lateinit var  listItem : Recipe



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoplistholder)

        // this will be the from the shoplisthold.xml
        shopListItemRecyclerView= findViewById(R.id.shopList)
        shopListItemRecyclerView.layoutManager = LinearLayoutManager(this)
        shopListItemRecyclerView.setHasFixedSize(true)

        // from activity_shopping_list.xml
//        shopListIngredRecyclerView = findViewById(R.id.shopListIngredient)
//        shopListIngredRecyclerView.layoutManager = LinearLayoutManager(this)
//        shopListIngredRecyclerView.setHasFixedSize(true)

        recipeIngredients = arrayListOf<ArrayList<Ingredient>>()
        recipeNames = arrayListOf<String>()

        //pass the database reference here as a parameter or make the call for the reference inside the functio
        getShoppingListData()

    }

    private fun getShoppingListData()
    {

        val currentUserID : String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        lateinit var  dbreference : DatabaseReference
        lateinit var  getReference : DataSnapshot

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
}

