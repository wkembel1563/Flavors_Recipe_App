package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text

class RecDisplayActivity : AppCompatActivity() {

   /* Names of Selected Ingredients */
   private lateinit var ingredientList : ArrayList<String>

   /* List of all dishes */
   private lateinit var databaseList : ArrayLi

   /* List of Relevant Dishes */
   private lateinit var dishList : ArrayList<Recipe>

   private  lateinit var  dbreference : DatabaseReference
   private  lateinit var  ingredDBref: DatabaseReference
   private  lateinit var  dataItemRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec_display)

        ingredientList = arrayListOf<String>()
        dishList = arrayListOf<Recipe>()

        dataItemRecyclerView = findViewById(R.id.recDishRecycler)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)

        /* Fetch list of ingredients selected */
        buildIngredientList(intent)

        /* Fetch list of dishes that use those ingredients */
        buildDishList()

        /* Build recycler view with the dishes */

    }

    /* build ingredient list from names passed from Recommendation Activity */
    private fun buildIngredientList(intent: Intent){
        /* Get ingredient list passed from Recommendation Activity */
        val bundle : Bundle ?= intent.extras
        if (bundle != null) {
            for (i in 0..bundle.size()){
                bundle.getString(i.toString())?.let { ingredientList.add(it) }
            }
        }
    }

    /* fetch dishes from database that use the selected ingredients */
    private fun buildDishList(){

        // for now use arbitrary single country to populate
        dbreference = FirebaseDatabase.getInstance().getReference().child("kembel_test_tree")
        dbreference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    // Dummy Item for Header
//                    val dummy = Recipe("", "", "", "", "", "")
//                    dataArrayList.add(dummy)

                    /* visit each dish in each country */
                    for (countrySnapshot in snapshot.children){
                        for (dishSnapshot in countrySnapshot.children){

                            /* extract dish recipe */
                            val dataItem = dishSnapshot.getValue(Recipe::class.java)

                            /* check if it contains requested ingredients */
                            var valid = false
                            if (dataItem != null) {
                                valid = checkIngredients(dataItem.Place.toString(), dataItem.Recipe.toString())
                            }

                            /* add valid dish to list */
                            if (valid){
                                dishList.add(dataItem!!)  // !! checks that object is not null
                            }

                        }
                    }

                    //go to DishView Activity on click
                    var adapter = RecDishAdapter(dishList)
                    dataItemRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : RecDishAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            // when a card is clicked, go to DishView
                            val intent = Intent(this@RecDisplayActivity, DishViewActivity::class.java)

                            // pass dish data to DishView
                            intent.putExtra("dish_Place", dishList[position].Place)
                            intent.putExtra("dish_Recipe", dishList[position].Recipe)
                            intent.putExtra("dish_CookTime", dishList[position].CookTime)
                            intent.putExtra("dish_PrepTime", dishList[position].PrepTime)
                            intent.putExtra("dish_Instructions", dishList[position].Instructions)

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

    /* Check if the ingredients for a particular recipe contain one of those selected */
    private fun checkIngredients(countryName : String, recipeName : String): Boolean{
       var result = false;

        /* connect to node */
        ingredDBref = FirebaseDatabase.getInstance().getReference()
            .child("kembel_test_tree")
            .child(countryName)
            .child(recipeName)
            .child("Ingredients")

        /* iterate through ingredients and check them */
        ingredDBref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ingredientSnapshot in snapshot.children){

                    /* mark a recipe as a match if it has at least one of the ingredients */
                    val ingredient = ingredientSnapshot.getValue(Ingredient::class.java)
                    val nameToCheck = ingredient?.name.toString()
                    for (name in ingredientList){
                        if (nameToCheck == name){
                            result = true
                            break
                        }
                    }
                    if(result) break
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return result
    }

}