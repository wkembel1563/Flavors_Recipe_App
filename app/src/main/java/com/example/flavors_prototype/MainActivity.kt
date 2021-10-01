package com.example.flavors_prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.flavors_prototype.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    // set up view-binding
    // its an alternative method of accessing UI resources like buttons
    private lateinit var binding: ActivityMainBinding

    // database object to access firebase
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // upload button click behavior
        // send user-defined country, recipe name to firebase under 'Recipes' path
        binding.uploadButton.setOnClickListener{

            // store input from country/recipe text boxes
            val country = binding.country.text.toString()
            val recipe_name = binding.recipeName.text.toString()

            // prepare a path in database
            database = FirebaseDatabase.getInstance().getReference("Recipes")

            // store recipe data in 'Recipe' data object defined in Recipe.kt
            var recipe = Recipe(country, recipe_name)

            // send recipe object to the path you prepared in firebase
            // EACH SET OF DATA WILL BE IDENTIFIED BY THE RECIPE NAME
            database.child(recipe_name).setValue(recipe).addOnSuccessListener {

                // text boxes can be reset when upload succeeds
                binding.country.text.clear()
                binding.recipeName.text.clear()

                Toast.makeText(this, "Recipe Successfully Uploaded!", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show()

            }
        }


        // PULL FROM FIREBASE Activity
        // Move to Activity2 when pull button is pressed
        val pull_button = findViewById<Button>(R.id.pull_button)
        pull_button.setOnClickListener {
            val Intent = Intent(this, Activity2::class.java)
            startActivity(Intent)
        }
    }
}
