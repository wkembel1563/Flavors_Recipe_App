package com.example.flavors_prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.flavors_prototype.databinding.Activity2Binding
import com.google.firebase.database.DatabaseReference

class Activity2 : AppCompatActivity() {

    // prepare to access xml objects for Activity 2
    private lateinit var binding2 : Activity2Binding

    // this will be used to store an instance of firebase
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = Activity2Binding.inflate(layoutInflater)
        setContentView(binding2.root)

        // PULL DATA button behavior
        // For testing: it should replace country and recipe name txt with China and Baozi
        // This data is stored under the recipe name in "Recipes" path head
        binding2.readButton.setOnClickListener {

            // get 'Baozi' data by default
            // data consists of country and recipe name
            database.child("Baozi").get().addOnSuccessListener {

                if(it.exists()){

                    // it = instance of the recipe datapoint under "Recipes"
                    // it's children are the data we will extract
                    val country = it.child("country").value
                    val recipe_name = it.child("recipe_name").value
                    Toast.makeText(this, "Read Successful", Toast.LENGTH_SHORT).show()

                    // update text boxes with real data
                    binding2.countryRead.text = country.toString()
                    binding2.recipeNameRead.text = recipe_name.toString()

                }else{

                    // data was not found
                    Toast.makeText(this, "What Child You Talkin' Bout??", Toast.LENGTH_SHORT).show()

                }

            }.addOnFailureListener {

                // process failed
                Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show()

            }

        }

    }


}
