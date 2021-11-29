package com.example.flavors_prototype

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import org.w3c.dom.Text

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
        setContentView(R.layout.activity_recommendation)

        ingredientList = arrayListOf<Ingredient>()
        selectedIngredients = arrayListOf<Ingredient>()

        /* Set up autocomplete content */
        setUpAutoInput()

        /* Set up recycler view to display selected ingredients */
        ingredientRecyclerView = findViewById<RecyclerView>(R.id.ingredientRecyclerView)
        ingredientRecyclerView.layoutManager = LinearLayoutManager(this)
        ingredientRecyclerView.setHasFixedSize(true)

        /* Commit ingredient selection from autocomplete to list with button */
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

            adapterRecView.setOnItemClickListener(object :  IngredientAdapter.OnItemClickListener {
                override fun onDeleteClick(position: Int) {
                    removeItem(position)
                }
            })
        }
    }

//    /* Delete card from recycler view when trash can is selected */
//    private fun deleteItem(item : String){
//
//        /* Remove ingredient from selected list */
//        var position = 0
//        for (ingredient in selectedIngredients){
//            if (item == ingredient.name.toString()){
//                position = selectedIngredients.indexOf(ingredient)
//                selectedIngredients.remove(ingredient)
//                break
//            }
//        }
//
//        /* Update Adapter */
//        var adapterRecView = IngredientAdapter(selectedIngredients)
//        ingredientRecyclerView.adapter = adapterRecView
//        //adapterRecView.notifyItemRemoved(position)
//
//    }

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


    /* Allow user to edit menu */

    /* Get list of all dishes. Map them to a number of ingredients hits. */

    /* Show in descending order */

}