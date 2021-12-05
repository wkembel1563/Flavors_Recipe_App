package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text

// TODO: rank the list

class RecDisplayActivity : AppCompatActivity() {

   /* Names of Selected Ingredients */
   private lateinit var ingredientList : ArrayList<String>

   /* List of Dishes To Be Displayed */
   private lateinit var dishList : ArrayList<Recipe>

   /* Number of Requested Ingredients Found In Each Dish in dishList*/
   /* Used to Sort List Before Displaying */
   private lateinit var hitList : ArrayList<Int>

   private  lateinit var  dbreference : DatabaseReference
   private  lateinit var  dataItemRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rec_display)

        ingredientList = arrayListOf<String>()
        dishList = arrayListOf<Recipe>()
        hitList = arrayListOf<Int>()

        dataItemRecyclerView = findViewById(R.id.recDishRecycler)
        dataItemRecyclerView.layoutManager = LinearLayoutManager(this)
        dataItemRecyclerView.setHasFixedSize(true)

        /* Fetch list of ingredients selected */
        buildIngredientList(intent)

        /* Fetch list of dishes that use those ingredients */
        /* Then it sorts them and activates recycler view */
        buildDishList()

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

        /* Connect to root not of database */
        /* Structure: testtree > Country > Recipe > IngredientsList > Ingredients > name/url */
        dbreference = FirebaseDatabase.getInstance().getReference().child("kembel_test_tree")
        dbreference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    /* visit each dish in each country */
                    /* add them to list of recommended dishes if they contain
                    *  some of the selected ingredients */
                    visitCountries(snapshot)

                    /* Sort valid dishes by number of hits in selected ingredient list */
                    sortDishes()

                    /* Generate dish recycler view */
                    displayDishes()

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /* Check Ingredients */
    /* Check if the ingredients for a particular recipe contain one of those selected */
    /* Params: single ingredient from recipe in database */
    /* Returns: true of false depending on if found in selected ingred list */
    private fun checkIngredients(nameToCheck : String): Boolean{
       var result = false;

        /* Check list of selected ingredients for given name */
        for (name in ingredientList){
            if (nameToCheck == name){
                result = true
                break
            }
        }

        return result
    }

    /* Sort dishes */
    /* ___ sort dishes in decreasing order based on their hit count */
    /* the more ingredients they had in common with those selected, the higher they are in list */
    private fun sortDishes(){

    }

    /* Display Dishes */
    /* Generate recyclerview of final list of dishes */
    /* Connect to DishView activity when a dish is clicked */
    private fun displayDishes(){

        /* Build Recycler View */
        //var adapter = RecDishAdapter(dishList)
        var adapter = DishAdapter(dishList)
        dataItemRecyclerView.adapter = adapter

        /* Move to DishView when recipe is clicked */
        //adapter.setOnItemClickListener(object : RecDishAdapter.OnItemClickListener{
        adapter.setOnItemClickListener(object : DishAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {

                val intent = Intent(this@RecDisplayActivity, DishViewActivity::class.java)

                // pass dish data to DishView
                // ingredients will be dealt with in dishview directly
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

    private fun visitCountries(snapshot: DataSnapshot){

        for (countrySnapshot in snapshot.children){     // testtree > country
            for (dishSnapshot in countrySnapshot.children){  // country > Recipe

                /* keeps track of num of target ingredients found in dish */
                var hitcount = 0

                /* extract dish recipe */
                val dataItem = dishSnapshot.getValue(Recipe::class.java)

                /* check if recipe contains requested ingredients */
                if (dataItem != null) {

                    /* Probe ingredients node of the dish */
                    val ingredNode = dishSnapshot.child("Ingredients")

                    /* Compare each ingredient in node against req ingred list */
                    var hit = false
                    for(ingredSnapshot in ingredNode.children){

                        /* check if ingredient is in list */
                        hit = checkIngredients(ingredSnapshot.key.toString())

                        /* if valid add to hitcount for this dish */
                        if (hit){
                            hitcount++
                        }

                        /* reset for next iteration */
                        hit = false
                    }
                }

                /* add valid dish to list */
                if (hitcount > 0){
                    dishList.add(dataItem!!)  // !! checks that object is not null
                    hitList.add(hitcount)
                }

            }
        }
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