package com.example.flavors_prototype.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flavors_prototype.R
import com.example.flavors_prototype.models.Ingredient
import com.example.flavors_prototype.views.IngredientAdapter
import com.google.firebase.database.*

class RecommendationActivity : AppCompatActivity() {

    /* Interface to firebase database */
    private lateinit var  dbreference : DatabaseReference

    /* Ingredient Containers */
    private lateinit var ingredientList : ArrayList<Ingredient>
    private lateinit var selectedIngredients : ArrayList<Ingredient>

    /* Ingredient List View */
    private lateinit var ingredientRecyclerView : RecyclerView
    private lateinit var adapterRecView : IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Enter Ingredients...";
        setContentView(R.layout.activity_recommendation)

        ingredientList = arrayListOf<Ingredient>()
        selectedIngredients = arrayListOf<Ingredient>()

        /* Set up autocomplete content */
        setUpAutoInput()

        /* Set up recycler view to display selected ingredients */
        ingredientRecyclerView = findViewById<RecyclerView>(R.id.ingredientRecyclerView)
        ingredientRecyclerView.layoutManager = LinearLayoutManager(this)
        ingredientRecyclerView.setHasFixedSize(true)

        /* Get ingredient selection from autocomplete when button is pressed */
        /* Send to ingredient list recycler view */
        val autoTextView : AutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.recview)
        val autoBtn : Button = findViewById<Button>(R.id.autoBtn)
        autoBtn.setOnClickListener {

            val str : String = autoTextView.text.toString()
            updateIngredList(str)

        }

        /* Move to MealRec Display Activity When 'Generate' is Selected */
        val generateBtn : Button = findViewById<Button>(R.id.generateBtn)
        generateBtn.setOnClickListener {
            val intent = Intent(this, RecDisplayActivity::class.java)

            /* pass ingredient names to display activity */
            for (ingredient in selectedIngredients){
                intent.putExtra(selectedIngredients.indexOf(ingredient).toString(), ingredient.name.toString())
            }

            startActivity(intent)
        }

    }

    /* Connect to Firebase, Place Ingredient Objects in List */
    fun setUpAutoInput(){
        dbreference = FirebaseDatabase.getInstance().getReference().child("Ingredients")
        dbreference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                    /* Fill ingredient list with known ingredients */
                    for (ingredientSnapshot in snapshot.children) {
                        val dataItem = ingredientSnapshot.getValue(Ingredient::class.java)
                        ingredientList.add(dataItem!!)
                    }

                    /* Create autocomplete suggestions list */
                    fillSuggestions(ingredientList)

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    /* Update Ingredient List with Selected Ingredients After Button Click */
    fun updateIngredList(str : String){

        /* search ingred array for item that matches input */
        /* when you find it, add it to display list */
        var found = false
        for (ingredient in ingredientList){
            if (str == ingredient.name.toString() && notSelected(str)){

                /* mark as found */
                found = true

                /* save object and stop search */
                selectedIngredients.add(ingredient)
                break

            }
        }

        /* show error if ingredient doesn't exist */
        if(!found){
            val text = "Ingredient Not Found"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }

        /* otherwise update selected ingredient list */
        else{

            /* run adapter to update recycler view with new list */
            adapterRecView = IngredientAdapter(selectedIngredients)
            ingredientRecyclerView.adapter = adapterRecView

            adapterRecView.setOnItemClickListener(object : IngredientAdapter.OnItemClickListener {
                override fun onDeleteClick(position: Int) {
                    removeItem(position)
                }
            })
        }
    }

    private fun removeItem(position : Int){
        selectedIngredients.removeAt(position)
        adapterRecView.notifyItemRemoved(position)
    }

    /* Check if the ingredient is already in the selection list */
    private fun notSelected(str: String): Boolean{

        var result = true

        for (selection in selectedIngredients){
            if (selection.name.toString() == str){
                result = false
            }
        }

        return result
    }

    /* Populate autocomplete */
    fun fillSuggestions(ingredientList : ArrayList<Ingredient>){

        /* extract ingredient names from list */
        val arraySize = ingredientList.size
        var ingredientNames = arrayOfNulls<String>(arraySize)
        var index = 0
        for(ingredientItem in ingredientList){
            ingredientNames[index] = ingredientItem.name.toString()
            index++
        }

        /* Pass ingredient names to autocomplete */
        var adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_expandable_list_item_1,  ingredientNames)
        var autoTextView = findViewById<AutoCompleteTextView>(R.id.recview)
        autoTextView.setAdapter(adapter)

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